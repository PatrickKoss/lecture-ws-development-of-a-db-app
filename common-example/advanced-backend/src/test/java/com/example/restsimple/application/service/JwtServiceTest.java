package com.example.restsimple.application.service;

import com.example.restsimple.domain.exception.InvalidTokenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(
            "test-secret-key-that-is-long-enough-for-hmac-sha384",
            15, // 15 minutes
            30 // 30 days
        );
    }

    @Test
    void generateAccessToken_ShouldReturnValidToken() {
        // Given
        Long adminId = 1L;
        String username = "testuser";

        // When
        String token = jwtService.generateAccessToken(adminId, username);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ")); // JWT header
    }

    @Test
    void generateRefreshToken_ShouldReturnValidToken() {
        // Given
        Long adminId = 1L;

        // When
        String token = jwtService.generateRefreshToken(adminId);

        // Then
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.startsWith("eyJ")); // JWT header
    }

    @Test
    void isTokenType_WithValidAccessToken_ShouldReturnTrue() {
        // Given
        Long adminId = 1L;
        String username = "testuser";
        String token = jwtService.generateAccessToken(adminId, username);

        // When
        boolean isValid = jwtService.isTokenType(token, "access");

        // Then
        assertTrue(isValid);
    }

    @Test
    void isTokenType_WithValidRefreshToken_ShouldReturnTrue() {
        // Given
        Long adminId = 1L;
        String token = jwtService.generateRefreshToken(adminId);

        // When
        boolean isValid = jwtService.isTokenType(token, "refresh");

        // Then
        assertTrue(isValid);
    }

    @Test
    void isTokenType_WithRefreshTokenAsAccess_ShouldReturnFalse() {
        // Given
        Long adminId = 1L;
        String refreshToken = jwtService.generateRefreshToken(adminId);

        // When
        boolean isValid = jwtService.isTokenType(refreshToken, "access");

        // Then
        assertFalse(isValid);
    }

    @Test
    void isTokenType_WithAccessTokenAsRefresh_ShouldReturnFalse() {
        // Given
        Long adminId = 1L;
        String username = "testuser";
        String accessToken = jwtService.generateAccessToken(adminId, username);

        // When
        boolean isValid = jwtService.isTokenType(accessToken, "refresh");

        // Then
        assertFalse(isValid);
    }

    @Test
    void extractUsername_WithValidAccessToken_ShouldReturnUsername() {
        // Given
        Long adminId = 1L;
        String username = "testuser";
        String token = jwtService.generateAccessToken(adminId, username);

        // When
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertEquals(username, extractedUsername);
    }

    @Test
    void extractAdminId_WithValidToken_ShouldReturnAdminId() {
        // Given
        Long adminId = 1L;
        String username = "testuser";
        String token = jwtService.generateAccessToken(adminId, username);

        // When
        Long extractedAdminId = jwtService.extractAdminId(token);

        // Then
        assertEquals(adminId, extractedAdminId);
    }

    @Test
    void isTokenType_WithInvalidToken_ShouldReturnFalse() {
        // Given
        String invalidToken = "invalid.token.here";

        // When
        boolean isValid = jwtService.isTokenType(invalidToken, "access");

        // Then
        assertFalse(isValid);
    }

    @Test
    void validateToken_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(InvalidTokenException.class, () -> jwtService.validateToken(invalidToken));
    }

    @Test
    void extractUsername_WithInvalidToken_ShouldThrowException() {
        // Given
        String invalidToken = "invalid.token.here";

        // When & Then
        assertThrows(InvalidTokenException.class, () -> jwtService.extractUsername(invalidToken));
    }

    @Test
    void validateToken_WithNullToken_ShouldThrowException() {
        // When & Then
        assertThrows(InvalidTokenException.class, () -> jwtService.validateToken(null));
    }

    @Test
    void isTokenType_WithNullToken_ShouldReturnFalse() {
        // When
        boolean isValid = jwtService.isTokenType(null, "access");

        // Then
        assertFalse(isValid);
    }

    @Test
    void extractUsername_WithNullToken_ShouldThrowException() {
        // When & Then
        assertThrows(InvalidTokenException.class, () -> jwtService.extractUsername(null));
    }

    @Test
    void isTokenType_WithEmptyToken_ShouldReturnFalse() {
        // When
        boolean isValid = jwtService.isTokenType("", "access");

        // Then
        assertFalse(isValid);
    }

    @Test
    void getAccessTokenExpirationMinutes_ShouldReturnCorrectValue() {
        // When
        int expirationTime = jwtService.getAccessTokenExpirationMinutes();

        // Then
        assertEquals(15, expirationTime);
    }

    @Test
    void getRefreshTokenExpirationDays_ShouldReturnCorrectValue() {
        // When
        int expirationTime = jwtService.getRefreshTokenExpirationDays();

        // Then
        assertEquals(30, expirationTime);
    }
}