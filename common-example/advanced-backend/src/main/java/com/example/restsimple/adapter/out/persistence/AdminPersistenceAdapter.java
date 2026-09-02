package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.AdminJpaEntity;
import com.example.restsimple.application.port.out.LoadAdminPort;
import com.example.restsimple.application.port.out.SaveAdminPort;
import com.example.restsimple.domain.model.Admin;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminPersistenceAdapter implements LoadAdminPort, SaveAdminPort {

    private final AdminJpaRepository adminRepository;

    public AdminPersistenceAdapter(AdminJpaRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public Optional<Admin> loadById(Long id) {
        return adminRepository.findById(id)
                .map(AdminJpaEntity::toDomain);
    }

    @Override
    public Optional<Admin> loadByUsername(String username) {
        return adminRepository.findByUsername(username)
                .map(AdminJpaEntity::toDomain);
    }

    @Override
    public Optional<Admin> loadByEmail(String email) {
        return adminRepository.findByEmail(email)
                .map(AdminJpaEntity::toDomain);
    }

    @Override
    public Admin save(Admin admin) {
        AdminJpaEntity entity = AdminJpaEntity.fromDomain(admin);
        AdminJpaEntity savedEntity = adminRepository.save(entity);
        return savedEntity.toDomain();
    }
}