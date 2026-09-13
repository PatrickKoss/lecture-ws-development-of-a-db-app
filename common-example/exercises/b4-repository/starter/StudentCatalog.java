package org.lecture;

import java.util.List;
import java.util.Optional;

public final class StudentCatalog {
    private final StudentRepository repository;

    public StudentCatalog(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> listStudents() {
        return repository.findAll();
    }

    public Optional<Student> findStudent(long id) {
        return repository.findById(id);
    }
}
