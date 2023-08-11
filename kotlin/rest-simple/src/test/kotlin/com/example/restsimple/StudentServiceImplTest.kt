package com.example.restsimple

import com.example.restsimple.model.Student
import com.example.restsimple.repository.StudentRepository
import com.example.restsimple.repository.UnitOfWork
import com.example.restsimple.repository.UpdateStudent
import com.example.restsimple.service.StudentServiceImpl
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.anyOrNull
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import java.util.*
import org.mockito.Mockito.`when` as whenever

@SpringBootTest
class StudentServiceImplTest {

    @Mock
    private lateinit var studentRepository: StudentRepository

    @Mock
    private lateinit var unitOfWork: UnitOfWork

    private lateinit var studentService: StudentServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        studentService = StudentServiceImpl(studentRepository, unitOfWork)
    }

    @Test
    fun `test get all students`() {
        val students = mutableListOf<Student?>(Student("1", "John", "Doe", LocalDateTime.now()))

        // Mock executeInTransaction to directly execute the passed lambda.
        whenever(unitOfWork.executeInTransaction<MutableList<Student?>>(anyOrNull())).thenAnswer {
            (it.arguments[0] as? Function0<MutableList<Student?>>)?.invoke()
        }

        whenever(studentRepository.findAll()).thenReturn(students)

        val result = studentService.allStudents

        assert(result.size == 1)
        assert(result[0]?.name == "John")
    }

    @Test
    fun `test create student`() {
        val student = Student("1", "John", "Doe", LocalDateTime.now())

        whenever(unitOfWork.executeInTransaction<Student?>(anyOrNull())).thenAnswer {
            (it.arguments[0] as? Function0<Student?>)?.invoke()
        }

        whenever(studentRepository.save(student)).thenReturn(student)

        val result = studentService.createStudent(student)

        assert(result?.name == "John")
    }

    @Test
    fun `test update student`() {
        val id = "1"
        val updatedStudent = UpdateStudent("Jane", "Doe")
        val existingStudent = Student("1", "John", "Doe", LocalDateTime.now())

        whenever(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(existingStudent))
        whenever(unitOfWork.executeInTransaction<Optional<out Student?>>(anyOrNull())).thenAnswer {
            (it.arguments[0] as? Function0<Optional<out Student?>>)?.invoke()
        }

        val result = studentService.updateStudent(id, updatedStudent)

        assert(result.isPresent)
        assert(result.get()?.name == "Jane")
    }

    @Test
    fun `test delete student`() {
        val id = "1"
        val student = Student("1", "John", "Doe", LocalDateTime.now())

        whenever(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(student))
        whenever(unitOfWork.executeInTransaction<Optional<out Student?>>(anyOrNull())).thenAnswer {
            (it.arguments[0] as? Function0<Optional<out Student?>>)?.invoke()
        }

        val result = studentService.deleteStudent(id)

        assert(result.isPresent)
    }

    @Test
    fun `test find student by id`() {
        val id = "1"
        val student = Student("1", "John", "Doe", LocalDateTime.now())

        whenever(studentRepository.findByIdColumn(id)).thenReturn(Optional.of(student))
        whenever(unitOfWork.executeInTransaction<Optional<Student>>(anyOrNull())).thenAnswer {
            (it.arguments[0] as? Function0<Optional<Student>>)?.invoke()
        }

        val result = studentService.findStudentById(id)

        assert(result.isPresent)
        assert(result.get().name == "John")
    }
}

