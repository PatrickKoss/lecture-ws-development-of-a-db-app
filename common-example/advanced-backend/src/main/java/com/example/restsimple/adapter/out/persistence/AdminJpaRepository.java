package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.AdminJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminJpaRepository extends JpaRepository<AdminJpaEntity, Long> {
    Optional<AdminJpaEntity> findByUsername(String username);
    Optional<AdminJpaEntity> findByEmail(String email);
}