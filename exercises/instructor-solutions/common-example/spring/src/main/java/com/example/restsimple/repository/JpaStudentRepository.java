package com.example.restsimple.repository;

import com.example.restsimple.domain.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaStudentRepository implements StudentRepository {
    private final SpringDataStudentRepository data;

    public JpaStudentRepository(SpringDataStudentRepository data) {
        this.data = data;
    }

    @Override public List<Student> findAll() {
        return data.findAll(Sort.by("id")).stream().map(StudentJpaEntity::toDomain).toList();
    }

    @Override public Optional<Student> findById(long id) {
        return data.findById(id).map(StudentJpaEntity::toDomain);
    }

    @Override public boolean existsByEmail(String email) { return data.existsByEmail(email); }
    @Override public boolean existsByStudentNumber(String number) {
        return data.existsByStudentNumber(number);
    }

    @Override public Student save(Student student) {
        try {
            return data.saveAndFlush(StudentJpaEntity.fromDomain(student)).toDomain();
        } catch (RuntimeException error) {
            throw SQLiteConstraintTranslator.translate(error);
        }
    }
}
