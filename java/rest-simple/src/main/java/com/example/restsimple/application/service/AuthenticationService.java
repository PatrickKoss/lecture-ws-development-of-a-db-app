package com.example.restsimple.application.service;

import com.example.restsimple.application.port.in.*;
import com.example.restsimple.application.port.out.*;
import com.example.restsimple.domain.exception.AdminNotFoundException;
import com.example.restsimple.domain.exception.AuthenticationException;
import com.example.restsimple.domain.exception.InvalidTokenException;
import com.example.restsimple.domain.model.Admin;
import com.example.restsimple.domain.model.RefreshToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class AuthenticationService implements RegisterAdminUseCase, LoginUseCase, RefreshTokenUseCase {

    private final LoadAdminPort loadAdminPort;
    private final SaveAdminPort saveAdminPort;
    private final LoadRefreshTokenPort loadRefreshTokenPort;
    private final SaveRefreshTokenPort saveRefreshTokenPort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthenticationService(
            LoadAdminPort loadAdminPort,
            SaveAdminPort saveAdminPort,
            LoadRefreshTokenPort loadRefreshTokenPort,
            SaveRefreshTokenPort saveRefreshTokenPort,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.loadAdminPort = loadAdminPort;
        this.saveAdminPort = saveAdminPort;
        this.loadRefreshTokenPort = loadRefreshTokenPort;
        this.saveRefreshTokenPort = saveRefreshTokenPort;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public Admin register(RegisterAdminCommand command) {
        if (loadAdminPort.loadByUsername(command.username()).isPresent()) {
            throw new IllegalArgumentException("Username already exists: " + command.username());
        }

        if (loadAdminPort.loadByEmail(command.email()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + command.email());
        }

        String hashedPassword = passwordEncoder.encode(command.password());
        Admin admin = Admin.createNew(
                command.username(),
                command.firstName(),
                command.lastName(),
                command.email(),
                hashedPassword
        );

        return saveAdminPort.save(admin);
    }

    @Override
    public LoginResponse login(LoginCommand command) {
        Admin admin = loadAdminPort.loadByUsername(command.username())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password"));

        if (!passwordEncoder.matches(command.password(), admin.getPasswordHash())) {
            throw new AuthenticationException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(admin.getId(), admin.getUsername());
        String refreshTokenJwt = jwtService.generateRefreshToken(admin.getId());

        LocalDateTime refreshTokenExpiry = LocalDateTime.now().plus(jwtService.getRefreshTokenExpirationDays(), ChronoUnit.DAYS);
        RefreshToken refreshToken = RefreshToken.createNew(refreshTokenJwt, admin.getId(), refreshTokenExpiry);
        saveRefreshTokenPort.save(refreshToken);

        int expiresIn = jwtService.getAccessTokenExpirationMinutes() * 60;

        return new LoginResponse(accessToken, refreshTokenJwt, expiresIn);
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenCommand command) {
        if (!jwtService.isTokenType(command.refreshToken(), "refresh")) {
            throw new InvalidTokenException("Invalid refresh token type");
        }

        Long adminId = jwtService.extractAdminId(command.refreshToken());

        RefreshToken refreshToken = loadRefreshTokenPort.loadByToken(command.refreshToken())
                .orElseThrow(() -> new InvalidTokenException("Refresh token not found"));

        if (!refreshToken.isValid()) {
            throw new InvalidTokenException("Refresh token is expired or revoked");
        }

        if (!refreshToken.getAdminId().equals(adminId)) {
            throw new InvalidTokenException("Refresh token does not belong to the authenticated user");
        }

        Admin admin = loadAdminPort.loadById(adminId)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with id: " + adminId));

        String newAccessToken = jwtService.generateAccessToken(admin.getId(), admin.getUsername());
        int expiresIn = jwtService.getAccessTokenExpirationMinutes() * 60;

        return new RefreshTokenResponse(newAccessToken, expiresIn);
    }

    public void revokeAllRefreshTokens(Long adminId) {
        loadRefreshTokenPort.revokeAllByAdminId(adminId);
    }
}