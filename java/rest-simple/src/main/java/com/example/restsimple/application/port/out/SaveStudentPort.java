package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Student;

public interface SaveStudentPort {
    Student saveStudent(Student student);
}