package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.RefreshToken;
import java.util.Optional;

public interface LoadRefreshTokenPort {
    Optional<RefreshToken> loadByToken(String token);
    void revokeAllByAdminId(Long adminId);
}