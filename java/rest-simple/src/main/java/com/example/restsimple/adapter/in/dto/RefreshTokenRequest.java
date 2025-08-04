package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.application.port.in.RefreshTokenCommand;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(
        @NotBlank(message = "Refresh token is required")
        @JsonProperty("refresh_token")
        String refreshToken
) {
    public RefreshTokenCommand toCommand() {
        return new RefreshTokenCommand(refreshToken);
    }
}