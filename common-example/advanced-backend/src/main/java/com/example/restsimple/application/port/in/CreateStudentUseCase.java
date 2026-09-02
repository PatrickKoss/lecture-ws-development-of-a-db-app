package com.example.restsimple.application.port.in;

import com.example.restsimple.domain.model.Student;

public interface CreateStudentUseCase {
    Student createStudent(CreateStudentCommand command);
    
    record CreateStudentCommand(String name, String lastName) {}
}