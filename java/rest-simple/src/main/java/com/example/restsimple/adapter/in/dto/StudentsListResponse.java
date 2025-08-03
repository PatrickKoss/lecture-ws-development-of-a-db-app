package com.example.restsimple.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class StudentsListResponse {
    
    @Schema(description = "List of students", example = "[{\"id\":\"550e8400-e29b-41d4-a716-446655440000\",\"mnr\":\"MNR001\",\"name\":\"John\",\"lastName\":\"Doe\",\"createdOn\":\"2023-01-01T12:00:00\"}]")
    private List<StudentResponse> students;

    public StudentsListResponse(List<StudentResponse> students) {
        this.students = students;
    }

    public List<StudentResponse> getStudents() {
        return students;
    }

    public void setStudents(List<StudentResponse> students) {
        this.students = students;
    }
}