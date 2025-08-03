package com.example.restsimple.application.port.in;

import com.example.restsimple.domain.model.Student;

import java.util.List;

public interface GetStudentUseCase {
    List<Student> getAllStudents();
    Student getStudentById(String id);
}