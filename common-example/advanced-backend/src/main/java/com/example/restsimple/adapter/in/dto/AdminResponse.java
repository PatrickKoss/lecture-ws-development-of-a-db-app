package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.domain.model.Admin;

public record AdminResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email
) {
    public static AdminResponse fromDomain(Admin admin) {
        return new AdminResponse(
                admin.getId(),
                admin.getUsername(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.getEmail()
        );
    }
}