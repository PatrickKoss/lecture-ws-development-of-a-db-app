package com.example.restsimple.response;

import com.example.restsimple.model.Student;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public class StudentListResponse {
    @NotNull(message = "students is required")
    public List<Student> students;

    public StudentListResponse(List<Student> students) {
        this.students = students;
    }
}
