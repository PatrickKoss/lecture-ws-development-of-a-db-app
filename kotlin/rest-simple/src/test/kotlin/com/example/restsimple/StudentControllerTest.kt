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
        mockMvc.perform(get("/healthz"))
                .andExpect(status().isOk)
                .andExpect(content().string("{\"message\":\"OK\"}"))
    }

    @Test
    fun `test get all students`() {
        val student = Student("1", "John", "Doe", LocalDateTime.now())
        whenever(studentService.allStudents).thenReturn(mutableListOf(student))

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.students[0].name").value("John"))
    }

    @Test
    fun `test create student`() {
        val studentRequest = StudentCreateRequest("John", "Doe")
        val student = Student("1", "John", "Doe", LocalDateTime.now())
        whenever(studentService.createStudent(anyOrNull())).thenReturn(student)

        mockMvc.perform(
                post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentRequest))
        )
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.student.name").value("John"))
    }

    @Test
    fun `test successful student update`() {
        val studentId = "1234"
        val studentUpdateRequest = StudentUpdateRequest("James", "Bond")
        val updatedStudent = Student(studentId, "James", "Bond", LocalDateTime.now())

        whenever(studentService.updateStudent(anyOrNull(), anyOrNull()))
                .thenReturn(Optional.of(updatedStudent))

        mockMvc.perform(
                put("/students/$studentId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentUpdateRequest))
        )
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.student.id").value(studentId))
                .andExpect(jsonPath("$.student.name").value("James"))
                .andExpect(jsonPath("$.student.lastName").value("Bond"))
    }

    @Test
    fun `test update student not found`() {
        val studentUpdateRequest = StudentUpdateRequest("Jane", "Doe")
        whenever(studentService.updateStudent("1", UpdateStudent(studentUpdateRequest.name, studentUpdateRequest.lastName)))
                .thenReturn(Optional.empty())

        mockMvc.perform(
                put("/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentUpdateRequest))
        )
                .andExpect(status().isNotFound)
    }

    @Test
    fun `test delete student`() {
        val student = Student("1", "John", "Doe", LocalDateTime.now())
        whenever(studentService.deleteStudent("1")).thenReturn(Optional.of(student))

        mockMvc.perform(
                delete("/students/1")
        )
                .andExpect(status().isNoContent)
    }

    @Test
    fun `test delete student not found`() {
        whenever(studentService.deleteStudent("1")).thenReturn(Optional.empty())

        mockMvc.perform(
                delete("/students/1")
        )
                .andExpect(status().isNotFound)
    }
}

