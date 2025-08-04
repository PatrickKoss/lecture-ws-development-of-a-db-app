package com.example.restsimple.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenResponse(
        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("expires_in")
        int expiresIn
) {
    public static RefreshTokenResponse fromRefreshTokenResponse(com.example.restsimple.application.port.in.RefreshTokenResponse response) {
        return new RefreshTokenResponse(
                response.accessToken(),
                response.expiresIn()
        );
    }
}