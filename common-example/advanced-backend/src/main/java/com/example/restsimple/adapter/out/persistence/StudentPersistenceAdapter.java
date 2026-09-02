package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.StudentJpaEntity;
import com.example.restsimple.application.port.out.DeleteStudentPort;
import com.example.restsimple.application.port.out.LoadStudentPort;
import com.example.restsimple.application.port.out.SaveStudentPort;
import com.example.restsimple.domain.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentPersistenceAdapter implements LoadStudentPort, SaveStudentPort, DeleteStudentPort {

    private static final Logger logger = LoggerFactory.getLogger(StudentPersistenceAdapter.class);

    private final StudentJpaRepository studentJpaRepository;

    public StudentPersistenceAdapter(StudentJpaRepository studentJpaRepository) {
        this.studentJpaRepository = studentJpaRepository;
    }

    @Override
    public Optional<Student> loadStudent(String id) {
        logger.debug("Loading student by ID: {}", id);
        
        Optional<Student> student = studentJpaRepository.findByIdColumn(id)
                .map(StudentJpaEntity::toDomain);
                
        if (student.isPresent()) {
            logger.debug("Successfully loaded student: {}", id);
        } else {
            logger.debug("Student not found with ID: {}", id);
        }
        
        return student;
    }

    @Override
    public List<Student> loadAllStudents() {
        logger.debug("Loading all students from database");
        
        List<Student> students = studentJpaRepository.findAll()
                .stream()
                .map(StudentJpaEntity::toDomain)
                .toList();
                
        logger.debug("Successfully loaded {} students from database", students.size());
        return students;
    }

    @Override
    public Student saveStudent(Student student) {
        logger.debug("Saving student to database: {} {} (ID: {})", 
                    student.getName(), student.getLastName(), student.getId());
        
        StudentJpaEntity entity = StudentJpaEntity.fromDomain(student);
        StudentJpaEntity savedEntity = studentJpaRepository.save(entity);
        Student savedStudent = savedEntity.toDomain();
        
        logger.debug("Successfully saved student to database: {} {} (ID: {})", 
                    savedStudent.getName(), savedStudent.getLastName(), savedStudent.getId());
        
        return savedStudent;
    }

    @Override
    public void deleteStudent(String id) {
        logger.debug("Deleting student from database: {}", id);
        studentJpaRepository.deleteByIdColumn(id);
        logger.debug("Successfully deleted student from database: {}", id);
    }

    @Override
    public Optional<Student> deleteStudentAndReturn(String id) {
        logger.debug("Attempting atomic delete and return for student: {}", id);
        
        Optional<Student> student = loadStudent(id);
        if (student.isPresent()) {
            studentJpaRepository.deleteByIdColumn(id);
            logger.debug("Successfully deleted and returned student: {}", id);
        } else {
            logger.debug("Student not found for atomic delete: {}", id);
        }
        
        return student;
    }

    @Override
    public Optional<Student> updateStudentNames(String id, String name, String lastName) {
        logger.debug("Attempting atomic update for student: {} with names: {} {}", id, name, lastName);
        
        int updatedRows = studentJpaRepository.updateStudentNames(id, name, lastName);
        if (updatedRows > 0) {
            logger.debug("Successfully updated {} rows, loading updated student: {}", updatedRows, id);
            Optional<Student> updatedStudent = loadStudent(id);
            if (updatedStudent.isPresent()) {
                logger.debug("Successfully loaded updated student: {}", id);
            }
            return updatedStudent;
        } else {
            logger.debug("No rows updated for student: {} - student may not exist", id);
            return Optional.empty();
        }
    }
}