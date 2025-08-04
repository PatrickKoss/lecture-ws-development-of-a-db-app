package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Admin;

public interface SaveAdminPort {
    Admin save(Admin admin);
}