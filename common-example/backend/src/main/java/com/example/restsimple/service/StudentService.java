package com.example.restsimple.service;

import com.example.restsimple.domain.Student;
import com.example.restsimple.dto.CreateStudentRequest;
import com.example.restsimple.repository.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> findAll() {
        throw new UnsupportedOperationException("TODO C2: findAll implementieren");
    }

    public Student findById(long id) {
        throw new UnsupportedOperationException("TODO C2: findById und 404 implementieren");
    }

    public Student create(CreateStudentRequest request) {
        throw new UnsupportedOperationException("TODO C3: Konfliktregel und insert implementieren");
    }
}
