package com.example.restsimple.application.port.in;

public record RefreshTokenResponse(String accessToken, int expiresIn) {
}