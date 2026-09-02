package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Admin;
import java.util.Optional;

public interface LoadAdminPort {
    Optional<Admin> loadById(Long id);
    Optional<Admin> loadByUsername(String username);
    Optional<Admin> loadByEmail(String email);
}