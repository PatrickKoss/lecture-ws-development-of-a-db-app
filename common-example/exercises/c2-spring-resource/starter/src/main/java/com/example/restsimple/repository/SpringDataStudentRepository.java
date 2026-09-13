package com.example.restsimple.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataStudentRepository extends JpaRepository<StudentJpaEntity, Long> {
    boolean existsByEmail(String email);
    boolean existsByStudentNumber(String studentNumber);
}
