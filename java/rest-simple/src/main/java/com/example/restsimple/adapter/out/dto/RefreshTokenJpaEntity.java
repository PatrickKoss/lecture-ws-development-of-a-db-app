package com.example.restsimple.adapter.out.dto;

import com.example.restsimple.converter.LocalDateTimeAttributeConverter;
import com.example.restsimple.domain.model.RefreshToken;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "refresh_token")
public class RefreshTokenJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", nullable = false, unique = true, length = 512)
    private String token;

    @Column(name = "admin_id", nullable = false)
    private Long adminId;

    @Column(name = "expires_at", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime expiresAt;

    @Column(name = "created_at", nullable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime createdAt;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    public RefreshTokenJpaEntity() {}

    public RefreshTokenJpaEntity(Long id, String token, Long adminId, LocalDateTime expiresAt, LocalDateTime createdAt, boolean revoked) {
        this.id = id;
        this.token = token;
        this.adminId = adminId;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.revoked = revoked;
    }

    public static RefreshTokenJpaEntity fromDomain(RefreshToken refreshToken) {
        return new RefreshTokenJpaEntity(
                refreshToken.getId(),
                refreshToken.getToken(),
                refreshToken.getAdminId(),
                refreshToken.getExpiresAt(),
                refreshToken.getCreatedAt(),
                refreshToken.isRevoked()
        );
    }

    public RefreshToken toDomain() {
        return new RefreshToken(id, token, adminId, expiresAt, createdAt, revoked);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshTokenJpaEntity that = (RefreshTokenJpaEntity) o;
        return revoked == that.revoked &&
                Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(adminId, that.adminId) &&
                Objects.equals(expiresAt, that.expiresAt) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, token, adminId, expiresAt, createdAt, revoked);
    }
}