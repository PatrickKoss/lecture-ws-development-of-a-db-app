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
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class StudentService implements CreateStudentUseCase, GetStudentUseCase, UpdateStudentUseCase, DeleteStudentUseCase {

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
        String id = UUID.randomUUID().toString();
        LocalDateTime createdOn = LocalDateTime.now();
        
        Student student = new Student(id, null, command.name(), command.lastName(), createdOn);
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
        Student existingStudent = loadStudentPort.loadStudent(command.id())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + command.id()));
        
        Student updatedStudent = existingStudent.withUpdatedInfo(command.name(), command.lastName());
        return saveStudentPort.saveStudent(updatedStudent);
    }

    @Override
    public Student deleteStudent(String id) {
        Student student = loadStudentPort.loadStudent(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id " + id));
        
        deleteStudentPort.deleteStudent(id);
        return student;
    }
}