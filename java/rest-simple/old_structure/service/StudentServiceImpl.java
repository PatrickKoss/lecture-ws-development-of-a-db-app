package com.example.restsimple.service;

import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import com.example.restsimple.repository.UnitOfWork;
import com.example.restsimple.repository.UpdateStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UnitOfWork unitOfWork;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, UnitOfWork unitOfWork) {
        this.studentRepository = studentRepository;
        this.unitOfWork = unitOfWork;
    }

    @Override
    public List<Student> getAllStudents() {
        return unitOfWork.executeInTransaction(studentRepository::findAll);
    }

    @Override
    public Student createStudent(Student student) {
        return unitOfWork.executeInTransaction(() -> studentRepository.save(student));
    }

    @Override
    public Optional<Student> updateStudent(String id, UpdateStudent updatedStudent) {
        return unitOfWork.executeInTransaction(() -> {
            Optional<Student> existingStudent = studentRepository.findByIdColumn(id);
            existingStudent.ifPresent(student -> {
                student.setName(updatedStudent.getName());
                student.setLastName(updatedStudent.getLastName());
                studentRepository.save(student);
            });

            return existingStudent;
        });
    }

    @Override
    public Optional<Student> deleteStudent(String id) {
        return unitOfWork.executeInTransaction(() -> {
            Optional<Student> student = studentRepository.findByIdColumn(id);
            student.ifPresent(studentRepository::delete);
            return student;
        });
    }

    @Override
    public Optional<Student> findStudentById(String id) {
        return unitOfWork.executeInTransaction(() -> studentRepository.findByIdColumn(id));
    }
}

