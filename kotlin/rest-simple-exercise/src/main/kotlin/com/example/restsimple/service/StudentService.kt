package com.example.restsimple.service

import com.example.restsimple.model.Student
import com.example.restsimple.repository.UpdateStudent
import java.util.*

interface StudentService {
    val allStudents: MutableList<Student?>
    fun createStudent(student: Student): Student?
    fun updateStudent(id: String?, updatedStudent: UpdateStudent): Optional<out Student?>
    fun deleteStudent(id: String?): Optional<out Student?>
    fun findStudentById(id: String?): Optional<Student>
}
