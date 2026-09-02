package com.example.restsimple.application.port.in;

import com.example.restsimple.domain.model.Student;

public interface UpdateStudentUseCase {
    Student updateStudent(UpdateStudentCommand command);
    
    record UpdateStudentCommand(String id, String name, String lastName) {}
}