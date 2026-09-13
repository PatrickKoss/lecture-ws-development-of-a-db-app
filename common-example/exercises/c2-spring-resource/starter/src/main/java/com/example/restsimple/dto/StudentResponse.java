package com.example.restsimple.dto;

import com.example.restsimple.domain.Student;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung eines Studierenden")
public record StudentResponse() {
    // TODO C1: Ergänzt die sechs Felder aus der Feldspezifikation.
    public static StudentResponse from(Student student) {
        throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
    }
}
