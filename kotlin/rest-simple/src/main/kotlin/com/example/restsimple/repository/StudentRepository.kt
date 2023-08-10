package com.example.restsimple.repository

import com.example.restsimple.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface StudentRepository : JpaRepository<Student, String> {
    @Query("SELECT s FROM Student s WHERE s.id = :id")
    fun findByIdColumn(@Param("id") id: String?): Optional<Student>

    @Query("DELETE FROM Student s WHERE s.id = :id")
    @Modifying
    fun deleteByIdColumn(@Param("id") id: String?)
}
