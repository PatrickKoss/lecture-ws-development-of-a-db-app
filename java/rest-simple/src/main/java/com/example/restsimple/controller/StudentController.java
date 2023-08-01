package com.example.restsimple.controller;

import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @PostMapping("/students")
    public Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody Student updatedStudent) {
        return studentRepository.findById(id).map(student -> {
            student.setMnr(updatedStudent.getMnr());
            student.setName(updatedStudent.getName());
            student.setLastName(updatedStudent.getLastName());
            student.setCreatedOn(updatedStudent.getCreatedOn());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    @DeleteMapping("/students/{id}")
    public void deleteStudent(@PathVariable String id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id " + id);
        }
        studentRepository.deleteById(id);
    }
}