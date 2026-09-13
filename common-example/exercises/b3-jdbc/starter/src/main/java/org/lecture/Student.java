package org.lecture;

import java.time.LocalDate;

public record Student(
        Long id,
        String firstName,
        String lastName,
        String email,
        String studentNumber,
        LocalDate enrollmentDate) {}
