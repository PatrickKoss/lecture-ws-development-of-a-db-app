package com.example.restsimple.application.port.out;

import com.example.restsimple.domain.model.Student;
import java.util.Optional;

public interface DeleteStudentPort {
    void deleteStudent(String id);
    
    /**
     * Atomically deletes a student by ID and returns the deleted student if it existed.
     * Returns empty Optional if no student was found with the given ID.
     */
    Optional<Student> deleteStudentAndReturn(String id);
}