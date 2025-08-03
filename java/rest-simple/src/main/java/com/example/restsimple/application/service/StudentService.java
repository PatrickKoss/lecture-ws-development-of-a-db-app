package com.example.restsimple.application.service;

import com.example.restsimple.application.port.in.*;
import com.example.restsimple.application.port.out.DeleteStudentPort;
import com.example.restsimple.application.port.out.LoadStudentPort;
import com.example.restsimple.application.port.out.SaveStudentPort;
import com.example.restsimple.domain.exception.StudentNotFoundException;
import com.example.restsimple.domain.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@Transactional
public class StudentService implements CreateStudentUseCase, GetStudentUseCase, UpdateStudentUseCase, DeleteStudentUseCase {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);
    
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-ZäöüÄÖÜß\\s-']+$");
    private static final int MIN_NAME_LENGTH = 2;
    private static final int MAX_NAME_LENGTH = 50;
    private static final int MAX_ACTIVE_STUDENTS = 10000;

    private final LoadStudentPort loadStudentPort;
    private final SaveStudentPort saveStudentPort;
    private final DeleteStudentPort deleteStudentPort;

    public StudentService(LoadStudentPort loadStudentPort, SaveStudentPort saveStudentPort, DeleteStudentPort deleteStudentPort) {
        this.loadStudentPort = loadStudentPort;
        this.saveStudentPort = saveStudentPort;
        this.deleteStudentPort = deleteStudentPort;
    }

    @Override
    public Student createStudent(CreateStudentCommand command) {
        logger.info("Creating new student: {} {}", command.name(), command.lastName());
        
        try {
            validateStudentNames(command.name(), command.lastName());
            checkStudentCapacity();
            preventDuplicateStudent(command.name(), command.lastName());
            
            String id = UUID.randomUUID().toString();
            String studentNumber = generateStudentNumber();
            LocalDateTime createdOn = LocalDateTime.now();
            
            logger.debug("Generated student ID: {}, student number: {}", id, studentNumber);
            
            Student student = new Student(id, studentNumber, command.name(), command.lastName(), createdOn);
            Student savedStudent = saveStudentPort.saveStudent(student);
            
            logger.info("Successfully created student with ID: {} and student number: {}", 
                       savedStudent.getId(), savedStudent.getMnr());
            return savedStudent;
        } catch (Exception e) {
            logger.error("Failed to create student: {} {} - Error: {}", 
                        command.name(), command.lastName(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        logger.debug("Retrieving all students");
        
        List<Student> students = loadStudentPort.loadAllStudents();
        logger.info("Retrieved {} students", students.size());
        
        return students;
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(String id) {
        logger.debug("Retrieving student by ID: {}", id);
        
        return loadStudentPort.loadStudent(id)
                .map(student -> {
                    logger.info("Found student: {} {} (ID: {})", 
                               student.getName(), student.getLastName(), student.getId());
                    return student;
                })
                .orElseThrow(() -> {
                    logger.warn("Student not found with ID: {}", id);
                    return new StudentNotFoundException("Student not found with id " + id);
                });
    }

    @Override
    public Student updateStudent(UpdateStudentCommand command) {
        logger.info("Updating student ID: {} with new name: {} {}", 
                   command.id(), command.name(), command.lastName());
        
        try {
            validateStudentNames(command.name(), command.lastName());
            
            return saveStudentPort.updateStudentNames(command.id(), command.name(), command.lastName())
                    .map(updatedStudent -> {
                        logger.info("Successfully updated student: {} {} (ID: {})", 
                                   updatedStudent.getName(), updatedStudent.getLastName(), updatedStudent.getId());
                        return updatedStudent;
                    })
                    .orElseThrow(() -> {
                        logger.warn("Student not found for update with ID: {}", command.id());
                        return new StudentNotFoundException("Student not found with id " + command.id());
                    });
        } catch (IllegalArgumentException e) {
            logger.error("Validation failed for student update (ID: {}): {}", command.id(), e.getMessage());
            throw e;
        }
    }

    @Override
    public Student deleteStudent(String id) {
        logger.info("Deleting student with ID: {}", id);
        
        return deleteStudentPort.deleteStudentAndReturn(id)
                .map(deletedStudent -> {
                    logger.info("Successfully deleted student: {} {} (ID: {})", 
                               deletedStudent.getName(), deletedStudent.getLastName(), deletedStudent.getId());
                    return deletedStudent;
                })
                .orElseThrow(() -> {
                    logger.warn("Student not found for deletion with ID: {}", id);
                    return new StudentNotFoundException("Student not found with id " + id);
                });
    }

    private void validateStudentNames(String name, String lastName) {
        logger.debug("Validating student names: {} {}", name, lastName);
        validateNameFormat(name, "First name");
        validateNameFormat(lastName, "Last name");
        logger.debug("Student name validation passed");
    }

    private void validateNameFormat(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        
        String trimmedName = name.trim();
        
        if (trimmedName.length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(fieldName + " must be at least " + MIN_NAME_LENGTH + " characters long");
        }
        
        if (trimmedName.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(fieldName + " must not exceed " + MAX_NAME_LENGTH + " characters");
        }
        
        if (!NAME_PATTERN.matcher(trimmedName).matches()) {
            throw new IllegalArgumentException(fieldName + " contains invalid characters. Only letters, spaces, hyphens and apostrophes are allowed");
        }
    }

    private void checkStudentCapacity() {
        List<Student> allStudents = loadStudentPort.loadAllStudents();
        int currentCount = allStudents.size();
        logger.debug("Checking student capacity: {}/{}", currentCount, MAX_ACTIVE_STUDENTS);
        
        if (currentCount >= MAX_ACTIVE_STUDENTS) {
            logger.warn("Student capacity limit reached: {}/{}", currentCount, MAX_ACTIVE_STUDENTS);
            throw new IllegalStateException("Maximum student capacity reached (" + MAX_ACTIVE_STUDENTS + "). Cannot register new students.");
        }
    }

    private void preventDuplicateStudent(String name, String lastName) {
        logger.debug("Checking for duplicate student: {} {}", name, lastName);
        List<Student> existingStudents = loadStudentPort.loadAllStudents();
        
        boolean duplicateExists = existingStudents.stream()
                .anyMatch(student -> 
                    student.getName().trim().equalsIgnoreCase(name.trim()) &&
                    student.getLastName().trim().equalsIgnoreCase(lastName.trim())
                );
        
        if (duplicateExists) {
            logger.warn("Duplicate student detected: {} {}", name, lastName);
            throw new IllegalArgumentException("A student with the name '" + name + " " + lastName + "' already exists");
        }
        
        logger.debug("No duplicate student found for: {} {}", name, lastName);
    }


    private String generateStudentNumber() {
        int currentYear = Year.now().getValue();
        logger.debug("Generating student number for year: {}", currentYear);
        
        List<Student> studentsThisYear = loadStudentPort.loadAllStudents().stream()
                .filter(student -> student.getMnr() != null && student.getMnr().startsWith(String.valueOf(currentYear)))
                .toList();
        
        int nextSequence = studentsThisYear.size() + 1;
        String studentNumber = String.format("%d%04d", currentYear, nextSequence);
        
        logger.debug("Generated student number: {} (sequence: {} for {} students this year)", 
                    studentNumber, nextSequence, studentsThisYear.size());
        
        return studentNumber;
    }
}