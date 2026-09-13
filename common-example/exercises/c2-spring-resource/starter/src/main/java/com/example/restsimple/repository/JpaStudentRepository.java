package com.example.restsimple.repository;

import com.example.restsimple.domain.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaStudentRepository implements StudentRepository {
    private final SpringDataStudentRepository data;

    public JpaStudentRepository(SpringDataStudentRepository data) {
        this.data = data;
    }

    @Override public List<Student> findAll() {
        throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
    }

    @Override public Optional<Student> findById(long id) {
        throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
    }

    @Override public boolean existsByEmail(String email) {
        throw new UnsupportedOperationException("TODO C3: Exists-Methode verwenden");
    }
    @Override public boolean existsByStudentNumber(String number) {
        throw new UnsupportedOperationException("TODO C3: Exists-Methode verwenden");
    }

    @Override public Student save(Student student) {
        throw new UnsupportedOperationException("TODO C3: saveAndFlush und Constraint-Übersetzung verwenden");
    }
}
