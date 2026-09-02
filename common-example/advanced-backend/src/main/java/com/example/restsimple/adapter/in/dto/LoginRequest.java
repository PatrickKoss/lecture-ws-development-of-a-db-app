package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.application.port.in.LoginCommand;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password
) {
    public LoginCommand toCommand() {
        return new LoginCommand(username, password);
    }
}