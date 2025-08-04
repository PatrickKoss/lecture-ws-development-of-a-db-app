package com.example.restsimple.application.port.in;

import com.example.restsimple.domain.model.Admin;

public interface RegisterAdminUseCase {
    Admin register(RegisterAdminCommand command);
}