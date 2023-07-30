package com.example.restsimple.repository;

import com.example.restsimple.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {}
