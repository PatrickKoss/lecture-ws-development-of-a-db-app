package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.RefreshToken;

public interface SaveRefreshTokenPort {
    RefreshToken save(RefreshToken refreshToken);
}