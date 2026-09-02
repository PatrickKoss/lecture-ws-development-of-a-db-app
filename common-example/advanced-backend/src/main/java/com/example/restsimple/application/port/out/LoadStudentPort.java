package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface LoadStudentPort {
    Optional<Student> loadStudent(String id);
    List<Student> loadAllStudents();
}