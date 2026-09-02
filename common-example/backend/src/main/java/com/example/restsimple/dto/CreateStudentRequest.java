package com.example.restsimple.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/* TODO C3: Diese Regeln und die Feldliste werden in der Vorlesung hergeleitet. */
public record CreateStudentRequest(
        @NotBlank @Size(max = 100) String firstName,
        @NotBlank @Size(max = 100) String lastName,
        @NotBlank @Email String email,
        @NotBlank @Pattern(regexp = "M[0-9]{7}") String studentNumber) {}
