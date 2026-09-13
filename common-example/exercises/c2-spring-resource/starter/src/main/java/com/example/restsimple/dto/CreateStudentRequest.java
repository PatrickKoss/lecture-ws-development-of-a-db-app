package com.example.restsimple.dto;

import com.example.restsimple.service.CreateStudentCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe zum Anlegen einer Studentin oder eines Studenten")
public record CreateStudentRequest() {
    // TODO C1: Ergänzt die vier Eingabefelder. enrollmentDate vergibt der Server.
    // TODO C3: Ergänzt Bean Validation und die Command-Abbildung.
    public CreateStudentCommand toCommand() {
        throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
    }
}
