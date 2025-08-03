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

    @Override
    public Optional<Student> deleteStudentAndReturn(String id) {
        Optional<Student> student = loadStudent(id);
        if (student.isPresent()) {
            studentJpaRepository.deleteByIdColumn(id);
        }
        return student;
    }

    @Override
    public Optional<Student> updateStudentNames(String id, String name, String lastName) {
        int updatedRows = studentJpaRepository.updateStudentNames(id, name, lastName);
        if (updatedRows > 0) {
            return loadStudent(id);
        }
        return Optional.empty();
    }
}