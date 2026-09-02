package com.example.restsimple.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class RefreshToken {
    private final Long id;
    private final String token;
    private final Long adminId;
    private final LocalDateTime expiresAt;
    private final LocalDateTime createdAt;
    private final boolean revoked;

    public RefreshToken(Long id, String token, Long adminId, LocalDateTime expiresAt, LocalDateTime createdAt, boolean revoked) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        if (adminId == null) {
            throw new IllegalArgumentException("Admin ID cannot be null");
        }
        if (expiresAt == null) {
            throw new IllegalArgumentException("Expires at cannot be null");
        }

        this.id = id;
        this.token = token;
        this.adminId = adminId;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
        this.revoked = revoked;
    }

    public static RefreshToken createNew(String token, Long adminId, LocalDateTime expiresAt) {
        return new RefreshToken(null, token, adminId, expiresAt, LocalDateTime.now(), false);
    }

    public RefreshToken withId(Long id) {
        return new RefreshToken(id, this.token, this.adminId, this.expiresAt, this.createdAt, this.revoked);
    }

    public RefreshToken revoke() {
        return new RefreshToken(this.id, this.token, this.adminId, this.expiresAt, this.createdAt, true);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValid() {
        return !revoked && !isExpired();
    }

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public Long getAdminId() {
        return adminId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isRevoked() {
        return revoked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RefreshToken that = (RefreshToken) o;
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

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", adminId=" + adminId +
                ", expiresAt=" + expiresAt +
                ", createdAt=" + createdAt +
                ", revoked=" + revoked +
                '}';
    }
}