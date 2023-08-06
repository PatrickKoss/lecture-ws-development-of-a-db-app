package com.example.restsimple.service;

import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import com.example.restsimple.request.StudentUpdateRequest;
import com.example.restsimple.response.StudentUpdateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public void deleteByIdColumn(String id) {
        studentRepository.findByIdColumn(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        studentRepository.deleteByIdColumn(id);
    }

    @Transactional
    public StudentUpdateResponse updateStudent(String id, StudentUpdateRequest updatedStudent) {
        Student student = studentRepository.findByIdColumn(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        student.setName(updatedStudent.getName());
        student.setLastName(updatedStudent.getLastName());

        Student updated = studentRepository.save(student);

        return new StudentUpdateResponse(updated);
    }
}
