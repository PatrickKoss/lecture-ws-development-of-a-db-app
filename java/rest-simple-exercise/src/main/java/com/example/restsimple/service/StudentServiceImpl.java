package com.example.restsimple.service;

import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import com.example.restsimple.repository.UnitOfWork;
import com.example.restsimple.repository.UpdateStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UnitOfWork unitOfWork;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UnitOfWork unitOfWork) {
        this.studentRepository = studentRepository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>();
    }

    @Override
    public Student createStudent(Student student) {
        return new Student();
    }

    @Override
    public Optional<Student> updateStudent(String id, UpdateStudent updatedStudent) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> deleteStudent(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Student> findStudentById(String id) {
        return Optional.empty();
    }
}

