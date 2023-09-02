package com.example.restsimple.service;

import com.example.restsimple.model.Student;
import com.example.restsimple.repository.UpdateStudent;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> getAllStudents();

    Student createStudent(Student student);

    Optional<Student> updateStudent(String id, UpdateStudent updatedStudent);

    Optional<Student> deleteStudent(String id);

    Optional<Student> findStudentById(String id);
}

