package com.example.restsimple.application.service;

import com.example.restsimple.application.port.in.*;
import com.example.restsimple.application.port.out.DeleteStudentPort;
import com.example.restsimple.application.port.out.LoadStudentPort;
import com.example.restsimple.application.port.out.SaveStudentPort;
import com.example.restsimple.domain.exception.StudentNotFoundException;
import com.example.restsimple.domain.model.Student;
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
        validateStudentNames(command.name(), command.lastName());
        checkStudentCapacity();
        preventDuplicateStudent(command.name(), command.lastName());
        
        String id = UUID.randomUUID().toString();
        String studentNumber = generateStudentNumber();
        LocalDateTime createdOn = LocalDateTime.now();
        
        Student student = new Student(id, studentNumber, command.name(), command.lastName(), createdOn);
        return saveStudentPort.saveStudent(student);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return loadStudentPort.loadAllStudents();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(String id) {
        return loadStudentPort.loadStudent(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
    }

    @Override
    public Student updateStudent(UpdateStudentCommand command) {
        validateStudentNames(command.name(), command.lastName());
        
        return saveStudentPort.updateStudentNames(command.id(), command.name(), command.lastName())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + command.id()));
    }

    @Override
    public Student deleteStudent(String id) {
        return deleteStudentPort.deleteStudentAndReturn(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
    }

    private void validateStudentNames(String name, String lastName) {
        validateNameFormat(name, "First name");
        validateNameFormat(lastName, "Last name");
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
        if (allStudents.size() >= MAX_ACTIVE_STUDENTS) {
            throw new IllegalStateException("Maximum student capacity reached (" + MAX_ACTIVE_STUDENTS + "). Cannot register new students.");
        }
    }

    private void preventDuplicateStudent(String name, String lastName) {
        List<Student> existingStudents = loadStudentPort.loadAllStudents();
        
        boolean duplicateExists = existingStudents.stream()
                .anyMatch(student -> 
                    student.getName().trim().equalsIgnoreCase(name.trim()) &&
                    student.getLastName().trim().equalsIgnoreCase(lastName.trim())
                );
        
        if (duplicateExists) {
            throw new IllegalArgumentException("A student with the name '" + name + " " + lastName + "' already exists");
        }
    }


    private String generateStudentNumber() {
        int currentYear = Year.now().getValue();
        List<Student> studentsThisYear = loadStudentPort.loadAllStudents().stream()
                .filter(student -> student.getMnr() != null && student.getMnr().startsWith(String.valueOf(currentYear)))
                .toList();
        
        int nextSequence = studentsThisYear.size() + 1;
        return String.format("%d%04d", currentYear, nextSequence);
    }
}