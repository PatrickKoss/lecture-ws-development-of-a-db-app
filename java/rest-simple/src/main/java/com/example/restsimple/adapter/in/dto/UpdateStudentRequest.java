package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.application.port.in.UpdateStudentUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateStudentRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 200, message = "Name must not exceed 200 characters")
        String name,
        
        @NotBlank(message = "Last name is required")
        String lastName
) {
    public UpdateStudentUseCase.UpdateStudentCommand toCommand(String id) {
        return new UpdateStudentUseCase.UpdateStudentCommand(id, name, lastName);
    }
}