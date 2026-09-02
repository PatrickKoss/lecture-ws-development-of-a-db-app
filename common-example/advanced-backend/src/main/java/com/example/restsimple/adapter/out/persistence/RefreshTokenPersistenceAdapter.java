package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.RefreshTokenJpaEntity;
import com.example.restsimple.application.port.out.LoadRefreshTokenPort;
import com.example.restsimple.application.port.out.SaveRefreshTokenPort;
import com.example.restsimple.domain.model.RefreshToken;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RefreshTokenPersistenceAdapter implements LoadRefreshTokenPort, SaveRefreshTokenPort {

    private final RefreshTokenJpaRepository refreshTokenRepository;

    public RefreshTokenPersistenceAdapter(RefreshTokenJpaRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    public Optional<RefreshToken> loadByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(RefreshTokenJpaEntity::toDomain);
    }

    @Override
    public void revokeAllByAdminId(Long adminId) {
        refreshTokenRepository.revokeAllByAdminId(adminId);
    }

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        RefreshTokenJpaEntity entity = RefreshTokenJpaEntity.fromDomain(refreshToken);
        RefreshTokenJpaEntity savedEntity = refreshTokenRepository.save(entity);
        return savedEntity.toDomain();
    }
}