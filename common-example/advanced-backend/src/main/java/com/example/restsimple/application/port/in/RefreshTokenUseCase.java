package com.example.restsimple.application.port.in;

public interface RefreshTokenUseCase {
    RefreshTokenResponse refresh(RefreshTokenCommand command);
}