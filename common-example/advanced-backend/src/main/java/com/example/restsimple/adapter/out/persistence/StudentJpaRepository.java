package com.example.restsimple.adapter.out.persistence;

import com.example.restsimple.adapter.out.dto.StudentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StudentJpaRepository extends JpaRepository<StudentJpaEntity, String> {
    @Query("SELECT s FROM StudentJpaEntity s WHERE s.id = :id")
    Optional<StudentJpaEntity> findByIdColumn(@Param("id") String id);

    @Query("DELETE FROM StudentJpaEntity s WHERE s.id = :id")
    @Modifying
    void deleteByIdColumn(@Param("id") String id);
    
    @Query("UPDATE StudentJpaEntity s SET s.name = :name, s.lastName = :lastName WHERE s.id = :id")
    @Modifying
    int updateStudentNames(@Param("id") String id, @Param("name") String name, @Param("lastName") String lastName);
}