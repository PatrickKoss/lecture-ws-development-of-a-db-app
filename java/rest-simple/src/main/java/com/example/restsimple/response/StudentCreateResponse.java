package com.example.restsimple.response;

import com.example.restsimple.model.Student;

import javax.validation.constraints.NotNull;

public class StudentCreateResponse {
    @NotNull(message = "student is required")
    public Student student;

    public StudentCreateResponse(Student student) {
        this.student = student;
    }
}
