package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Student;
import java.util.Optional;

public interface SaveStudentPort {
    Student saveStudent(Student student);
    
    /**
     * Atomically updates a student's name and lastName by ID.
     * Returns the updated student if the ID exists, empty Optional otherwise.
     */
    Optional<Student> updateStudentNames(String id, String name, String lastName);
}