package com.example.restsimple.repository;

import com.example.restsimple.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    Optional<Student> findByIdColumn(@Param("id") String id);

    @Query("DELETE FROM Student s WHERE s.id = :id")
    @Modifying
    void deleteByIdColumn(@Param("id") String id);
}
