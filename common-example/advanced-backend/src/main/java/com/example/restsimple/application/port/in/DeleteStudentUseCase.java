package com.example.restsimple.application.port.in;

import com.example.restsimple.domain.model.Student;

public interface DeleteStudentUseCase {
    Student deleteStudent(String id);
}