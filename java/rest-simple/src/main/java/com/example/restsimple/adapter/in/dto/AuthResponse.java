package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.application.port.in.LoginResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("expires_in")
        int expiresIn
) {
    public static AuthResponse fromLoginResponse(LoginResponse loginResponse) {
        return new AuthResponse(
                loginResponse.accessToken(),
                loginResponse.refreshToken(),
                loginResponse.expiresIn()
        );
    }
}