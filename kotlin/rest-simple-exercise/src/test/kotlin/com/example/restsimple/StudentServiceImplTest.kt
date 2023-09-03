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
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test create student`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test update student`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test delete student`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test find student by id`() {
        throw UnsupportedOperationException("Not implemented");
    }
}

