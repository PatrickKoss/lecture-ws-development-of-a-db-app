package com.example.restsimple.dto;

import com.example.restsimple.domain.Student;
import java.time.LocalDate;

/* TODO C3: Entscheidet gemeinsam, welche Felder der HTTP-Vertrag zusagt. */
public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String studentNumber,
        LocalDate enrollmentDate) {
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
