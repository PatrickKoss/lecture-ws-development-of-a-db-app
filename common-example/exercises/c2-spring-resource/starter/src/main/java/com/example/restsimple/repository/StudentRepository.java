package com.example.restsimple.repository;

import com.example.restsimple.domain.Student;
import java.util.List;
import java.util.Optional;

public interface StudentRepository {
    List<Student> findAll();

    Optional<Student> findById(long id);

    boolean existsByEmail(String email);

    boolean existsByStudentNumber(String studentNumber);

    Student save(Student student);
}
