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
            val result = unitOfWork.executeInTransaction { studentRepository.findAll() }
            return result ?: mutableListOf()
        }

    override fun createStudent(student: Student): Student? {
        return unitOfWork.executeInTransaction { studentRepository.save(student) }
    }

    override fun updateStudent(id: String?, updatedStudent: UpdateStudent): Optional<out Student?> {
        return unitOfWork.executeInTransaction {
            val existingStudentOpt: Optional<Student> = studentRepository.findByIdColumn(id)

            existingStudentOpt.ifPresent { existingStudent ->
                existingStudent?.let {
                    it.name = updatedStudent.name
                    it.lastName = updatedStudent.lastName
                    studentRepository.save(it)
                }
            }

            existingStudentOpt
        } ?: Optional.empty<Student?>()
    }

    override fun deleteStudent(id: String?): Optional<out Student?> {
        return unitOfWork.executeInTransaction {
            val studentOpt: Optional<Student> = studentRepository.findByIdColumn(id)

            studentOpt.ifPresent { student ->
                student?.let { studentRepository.delete(it) }
            }

            studentOpt
        } ?: Optional.empty<Student?>()
    }

    override fun findStudentById(id: String?): Optional<Student> {
        val result = unitOfWork.executeInTransaction { studentRepository.findByIdColumn(id) }
        return result ?: Optional.empty()
    }
}
