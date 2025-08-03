package com.example.restsimple.adapter.in.web;

import com.example.restsimple.application.port.in.CreateStudentUseCase;
import com.example.restsimple.application.port.in.DeleteStudentUseCase;
import com.example.restsimple.application.port.in.GetStudentUseCase;
import com.example.restsimple.application.port.in.UpdateStudentUseCase;
import com.example.restsimple.domain.exception.StudentNotFoundException;
import com.example.restsimple.domain.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateStudentUseCase createStudentUseCase;

    @MockBean
    private GetStudentUseCase getStudentUseCase;

    @MockBean
    private UpdateStudentUseCase updateStudentUseCase;

    @MockBean
    private DeleteStudentUseCase deleteStudentUseCase;

    @Test
    void getAllStudents_ShouldReturnStudentList() throws Exception {
        // Given
        List<Student> students = List.of(
            new Student("1", "MNR001", "John", "Doe", LocalDateTime.now()),
            new Student("2", "MNR002", "Jane", "Smith", LocalDateTime.now())
        );
        when(getStudentUseCase.getAllStudents()).thenReturn(students);

        // When & Then
        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students.length()").value(2))
                .andExpect(jsonPath("$.students[0].name").value("John"))
                .andExpect(jsonPath("$.students[1].name").value("Jane"));
    }

    @Test
    void createStudent_WithValidData_ShouldReturnCreatedStudent() throws Exception {
        // Given
        Student createdStudent = new Student("1", "MNR001", "John", "Doe", LocalDateTime.now());
        when(createStudentUseCase.createStudent(any(CreateStudentUseCase.CreateStudentCommand.class)))
                .thenReturn(createdStudent);

        String requestBody = """
                {
                    "name": "John",
                    "lastName": "Doe"
                }
                """;

        // When & Then
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    void createStudent_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Given
        String requestBody = """
                {
                    "name": "",
                    "lastName": ""
                }
                """;

        // When & Then
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateStudent_WithValidData_ShouldReturnUpdatedStudent() throws Exception {
        // Given
        String id = "1";
        Student updatedStudent = new Student(id, "MNR001", "John Updated", "Doe Updated", LocalDateTime.now());
        when(updateStudentUseCase.updateStudent(any(UpdateStudentUseCase.UpdateStudentCommand.class)))
                .thenReturn(updatedStudent);

        String requestBody = """
                {
                    "name": "John Updated",
                    "lastName": "Doe Updated"
                }
                """;

        // When & Then
        mockMvc.perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.lastName").value("Doe Updated"));
    }

    @Test
    void updateStudent_WhenStudentNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        String id = "1";
        when(updateStudentUseCase.updateStudent(any(UpdateStudentUseCase.UpdateStudentCommand.class)))
                .thenThrow(new StudentNotFoundException("Student not found with id " + id));

        String requestBody = """
                {
                    "name": "John",
                    "lastName": "Doe"
                }
                """;

        // When & Then
        mockMvc.perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteStudent_WhenStudentExists_ShouldReturnNoContent() throws Exception {
        // Given
        String id = "1";
        Student student = new Student(id, "MNR001", "John", "Doe", LocalDateTime.now());
        when(deleteStudentUseCase.deleteStudent(id)).thenReturn(student);

        // When & Then
        mockMvc.perform(delete("/students/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteStudent_WhenStudentNotFound_ShouldReturnNotFound() throws Exception {
        // Given
        String id = "1";
        when(deleteStudentUseCase.deleteStudent(id))
                .thenThrow(new StudentNotFoundException("Student not found with id " + id));

        // When & Then
        mockMvc.perform(delete("/students/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void healthz_ShouldReturnOk() throws Exception {
        // When & Then
        mockMvc.perform(get("/healthz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("OK"));
    }
}