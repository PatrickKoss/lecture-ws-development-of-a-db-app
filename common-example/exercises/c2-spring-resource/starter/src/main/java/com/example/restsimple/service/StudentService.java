package com.example.restsimple.service;

import com.example.restsimple.domain.Student;
import com.example.restsimple.exception.ResourceConflictException;
import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.repository.StudentRepository;
import java.util.List;
import java.time.Clock;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository repository;
    private final Clock clock;

    public StudentService(StudentRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public List<Student> findAll() {
        throw new UnsupportedOperationException("TODO C2: Liste aus dem Repository lesen");
    }

    public Student findById(long id) {
        throw new UnsupportedOperationException("TODO C2: ID lesen und unbekannte ID als 404 melden");
    }

    @Transactional
    public Student create(CreateStudentCommand command) {
        throw new UnsupportedOperationException("TODO C3: Konflikte prüfen, Datum vergeben und speichern");
    }
}
