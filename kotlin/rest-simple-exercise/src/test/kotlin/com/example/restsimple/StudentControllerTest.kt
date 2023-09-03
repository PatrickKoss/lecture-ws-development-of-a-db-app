package com.example.restsimple

import com.example.restsimple.model.Student
import com.example.restsimple.repository.UpdateStudent
import com.example.restsimple.request.StudentCreateRequest
import com.example.restsimple.request.StudentUpdateRequest
import com.example.restsimple.service.StudentService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var studentService: StudentService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `test health endpoint`() {
        throw UnsupportedOperationException("Not implemented");
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
    fun `test successful student update`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test update student not found`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test delete student`() {
        throw UnsupportedOperationException("Not implemented");
    }

    @Test
    fun `test delete student not found`() {
        throw UnsupportedOperationException("Not implemented");
    }
}

