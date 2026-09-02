package com.example.restsimple.application.port.in;

public record LoginResponse(String accessToken, String refreshToken, int expiresIn) {
}