package com.example.restsimple.dto;

import com.example.restsimple.domain.Student;
import java.time.LocalDate;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung eines Studierenden")
public record StudentResponse(
        @Schema(description = "Vom Server vergebene ID", example = "1",
                accessMode = Schema.AccessMode.READ_ONLY, requiredMode = Schema.RequiredMode.REQUIRED) Long id,
        @Schema(description = "Vorname", example = "Lena", requiredMode = Schema.RequiredMode.REQUIRED) String firstName,
        @Schema(description = "Nachname", example = "Hoffmann", requiredMode = Schema.RequiredMode.REQUIRED) String lastName,
        @Schema(description = "Eindeutige E-Mail-Adresse", example = "lena.hoffmann@stud.example",
                requiredMode = Schema.RequiredMode.REQUIRED) String email,
        @Schema(description = "Eindeutige Matrikelnummer", example = "M2023001",
                requiredMode = Schema.RequiredMode.REQUIRED) String studentNumber,
        @Schema(description = "Vom Server gesetztes Einschreibedatum", example = "2023-10-01",
                accessMode = Schema.AccessMode.READ_ONLY,
                requiredMode = Schema.RequiredMode.REQUIRED) LocalDate enrollmentDate) {
    public static StudentResponse from(Student student) {
        return new StudentResponse(
                student.id(),
                student.firstName(),
                student.lastName(),
                student.email(),
                student.studentNumber(),
                student.enrollmentDate());
    }
}
