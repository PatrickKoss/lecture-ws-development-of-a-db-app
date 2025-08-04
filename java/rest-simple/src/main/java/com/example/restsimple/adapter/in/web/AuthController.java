package com.example.restsimple.adapter.in.web;

import com.example.restsimple.adapter.in.dto.*;
import com.example.restsimple.application.port.in.LoginUseCase;
import com.example.restsimple.application.port.in.RefreshTokenUseCase;
import com.example.restsimple.application.port.in.RegisterAdminUseCase;
import com.example.restsimple.domain.model.Admin;
import com.example.restsimple.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Authentication and registration endpoints")
public class AuthController {

    private final RegisterAdminUseCase registerAdminUseCase;
    private final LoginUseCase loginUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;

    public AuthController(
            RegisterAdminUseCase registerAdminUseCase,
            LoginUseCase loginUseCase,
            RefreshTokenUseCase refreshTokenUseCase) {
        this.registerAdminUseCase = registerAdminUseCase;
        this.loginUseCase = loginUseCase;
        this.refreshTokenUseCase = refreshTokenUseCase;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new admin", description = "Creates a new admin account with username, name, email and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Admin successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Admin admin = registerAdminUseCase.register(request.toCommand());
            AdminResponse response = AdminResponse.fromDomain(admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Registration failed", e.getMessage()));
        }
    }

    @PostMapping("/login")
    @Operation(summary = "Login with username and password", description = "Authenticates user and returns access and refresh tokens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            var loginResponse = loginUseCase.login(request.toCommand());
            AuthResponse response = AuthResponse.fromLoginResponse(loginResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Login failed", "Invalid username or password"));
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Uses refresh token to generate a new access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token refreshed successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        try {
            var refreshResponse = refreshTokenUseCase.refresh(request.toCommand());
            RefreshTokenResponse response = RefreshTokenResponse.fromRefreshTokenResponse(refreshResponse);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Token refresh failed", "Invalid or expired refresh token"));
        }
    }
}