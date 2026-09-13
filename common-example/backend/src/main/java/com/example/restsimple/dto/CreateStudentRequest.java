package com.example.restsimple.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.example.restsimple.service.CreateStudentCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe zum Anlegen einer Studentin oder eines Studenten")
public record CreateStudentRequest(
        @Schema(description = "Vorname", example = "Ada", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Size(max = 100) String firstName,
        @Schema(description = "Nachname", example = "Lovelace", requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Size(max = 100) String lastName,
        @Schema(description = "Eindeutige E-Mail-Adresse", example = "ada@campus.example",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Email String email,
        @Schema(description = "Matrikelnummer im Format M plus sieben Ziffern", example = "M2026999",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "M[0-9]{7}") String studentNumber) {
    public CreateStudentCommand toCommand() {
        return new CreateStudentCommand(firstName, lastName, email, studentNumber);
    }
}
