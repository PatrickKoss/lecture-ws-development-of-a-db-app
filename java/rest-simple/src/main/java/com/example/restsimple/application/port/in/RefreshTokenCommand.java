package com.example.restsimple.application.port.in;

public record RefreshTokenCommand(String refreshToken) {
    public RefreshTokenCommand {
        if (refreshToken == null || refreshToken.trim().isEmpty()) {
            throw new IllegalArgumentException("Refresh token cannot be null or empty");
        }
    }
}