package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.StudentJpaEntity;
import com.example.restsimple.application.port.out.DeleteStudentPort;
import com.example.restsimple.application.port.out.LoadStudentPort;
import com.example.restsimple.application.port.out.SaveStudentPort;
import com.example.restsimple.domain.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentPersistenceAdapter implements LoadStudentPort, SaveStudentPort, DeleteStudentPort {

    private final StudentJpaRepository studentJpaRepository;

    public StudentPersistenceAdapter(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    public Optional<Student> loadStudent(String id) {
        return studentJpaRepository.findByIdColumn(id)
                .map(StudentJpaEntity::toDomain);
    }

    @Override
    public List<Student> loadAllStudents() {
        return studentJpaRepository.findAll()
                .stream()
                .map(StudentJpaEntity::toDomain)
                .toList();
    }

    @Override
    public Student saveStudent(Student student) {
        StudentJpaEntity entity = StudentJpaEntity.fromDomain(student);
        StudentJpaEntity savedEntity = studentJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public void deleteStudent(String id) {
        studentJpaRepository.deleteByIdColumn(id);
    }
}