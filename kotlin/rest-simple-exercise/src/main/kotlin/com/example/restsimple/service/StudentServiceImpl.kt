package com.example.restsimple.service

import com.example.restsimple.model.Student
import com.example.restsimple.repository.StudentRepository
import com.example.restsimple.repository.UnitOfWork
import com.example.restsimple.repository.UpdateStudent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class StudentServiceImpl @Autowired constructor(
    private val studentRepository: StudentRepository,
    private val unitOfWork: UnitOfWork
) : StudentService {

    override val allStudents: MutableList<Student?>
        get() {
            return mutableListOf()
        }

    override fun createStudent(student: Student): Student? {
        return Student()
    }

    override fun updateStudent(id: String?, updatedStudent: UpdateStudent): Optional<out Student?> {
        return Optional.empty<Student?>()
    }

    override fun deleteStudent(id: String?): Optional<out Student?> {
        return Optional.empty<Student?>()
    }

    override fun findStudentById(id: String?): Optional<Student> {
        return Optional.empty()
    }
}
