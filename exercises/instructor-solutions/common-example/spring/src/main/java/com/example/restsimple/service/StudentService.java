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
        return repository.findAll();
    }

    public Student findById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Studierende Person nicht gefunden"));
    }

    @Transactional
    public Student create(CreateStudentCommand command) {
        if (repository.existsByStudentNumber(command.studentNumber())) {
            throw new ResourceConflictException("Matrikelnummer ist bereits vergeben");
        }
        if (repository.existsByEmail(command.email())) {
            throw new ResourceConflictException("E-Mail-Adresse ist bereits vergeben");
        }
        return repository.save(new Student(null, command.firstName(), command.lastName(),
                command.email(), command.studentNumber(), LocalDate.now(clock)));
    }
}
