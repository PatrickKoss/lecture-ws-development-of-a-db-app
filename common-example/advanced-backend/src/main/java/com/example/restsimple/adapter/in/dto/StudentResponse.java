package com.example.restsimple.adapter.in.dto;

import com.example.restsimple.domain.model.Student;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record StudentResponse(
        String id,
        String mnr,
        String name,
        String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdOn
) {
    public static StudentResponse fromDomain(Student student) {
        return new StudentResponse(
                student.getId(),
                student.getMnr(),
                student.getName(),
                student.getLastName(),
                student.getCreatedOn()
        );
    }
}