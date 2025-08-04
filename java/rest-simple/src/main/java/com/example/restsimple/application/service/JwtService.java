package com.example.restsimple.application.service;

import com.example.restsimple.domain.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final int accessTokenExpirationMinutes;
    private final int refreshTokenExpirationDays;

    public JwtService(
            @Value("${jwt.secret:mySecretKey1234567890123456789012345678901234567890}") String secret,
            @Value("${jwt.access-token-expiration-minutes:15}") int accessTokenExpirationMinutes,
            @Value("${jwt.refresh-token-expiration-days:30}") int refreshTokenExpirationDays) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
        this.accessTokenExpirationMinutes = accessTokenExpirationMinutes;
        this.refreshTokenExpirationDays = refreshTokenExpirationDays;
    }

    public String generateAccessToken(Long adminId, String username) {
        Instant now = Instant.now();
        Instant expiration = now.plus(accessTokenExpirationMinutes, ChronoUnit.MINUTES);

        return Jwts.builder()
                .subject(adminId.toString())
                .claim("username", username)
                .claim("type", "access")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String generateRefreshToken(Long adminId) {
        Instant now = Instant.now();
        Instant expiration = now.plus(refreshTokenExpirationDays, ChronoUnit.DAYS);

        return Jwts.builder()
                .subject(adminId.toString())
                .claim("type", "refresh")
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid or expired token: " + e.getMessage());
        }
    }

    public Long extractAdminId(String token) {
        Claims claims = validateToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public String extractUsername(String token) {
        Claims claims = validateToken(token);
        return claims.get("username", String.class);
    }

    public boolean isTokenType(String token, String expectedType) {
        try {
            Claims claims = validateToken(token);
            String tokenType = claims.get("type", String.class);
            return expectedType.equals(tokenType);
        } catch (InvalidTokenException e) {
            return false;
        }
    }

    public int getAccessTokenExpirationMinutes() {
        return accessTokenExpirationMinutes;
    }

    public int getRefreshTokenExpirationDays() {
        return refreshTokenExpirationDays;
    }
}