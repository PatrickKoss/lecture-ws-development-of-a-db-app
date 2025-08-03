package com.example.restsimple;

import com.example.restsimple.controller.StudentController;
import com.example.restsimple.model.Student;
import com.example.restsimple.repository.UpdateStudent;
import com.example.restsimple.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StudentControllerTest {

    private StudentService studentService;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        studentService = mock(StudentService.class);
        StudentController studentController = new StudentController(studentService);
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void testGetAllStudents() throws Exception {
        Student student = new Student("1", "John", "Doe", null);
        when(studentService.getAllStudents()).thenReturn(Collections.singletonList(student));

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students[0].id").value("1"))
                .andExpect(jsonPath("$.students[0].name").value("John"))
                .andExpect(jsonPath("$.students[0].lastName").value("Doe"));

        verify(studentService).getAllStudents();
    }

    @Test
    public void testCreateStudent() throws Exception {
        String name = "John";
        String lastName = "Doe";
        Student student = new Student(UUID.randomUUID().toString(), name, lastName, null);
        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + name + "\", \"lastName\":\"" + lastName + "\"}"))
                .andExpect(status().isCreated());

        verify(studentService).createStudent(any(Student.class));
    }

    @Test
    public void testUpdateStudent() throws Exception {
        String id = "1";
        Student student = new Student(id, "John", "Doe", null);
        when(studentService.updateStudent(eq(id), any(UpdateStudent.class))).thenReturn(Optional.of(student));

        mockMvc.perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"lastName\":\"Smith\"}"))
                .andExpect(status().isOk());

        verify(studentService).updateStudent(eq(id), any(UpdateStudent.class));
    }

    @Test
    public void testDeleteStudent() throws Exception {
        String id = "1";
        Student student = new Student(id, "John", "Doe", null);
        when(studentService.deleteStudent(id)).thenReturn(Optional.of(student));

        mockMvc.perform(delete("/students/" + id))
                .andExpect(status().isNoContent());

        verify(studentService).deleteStudent(id);
    }

    @Test
    public void testGetAllStudents_EmptyList() throws Exception {
        when(studentService.getAllStudents()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.students").isEmpty());

        verify(studentService).getAllStudents();
    }

    @Test
    public void testCreateStudent_MissingName() throws Exception {
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"lastName\":\"Doe\"}"))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    public void testCreateStudent_MissingLastName() throws Exception {
        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\"}"))
                .andExpect(status().isBadRequest());

        verify(studentService, never()).createStudent(any(Student.class));
    }

    @Test
    public void testUpdateStudent_NotFound() throws Exception {
        String id = "1";
        when(studentService.updateStudent(eq(id), any(UpdateStudent.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/students/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"John\", \"lastName\":\"Smith\"}"))
                .andExpect(status().isNotFound());

        verify(studentService).updateStudent(eq(id), any(UpdateStudent.class));
    }

    @Test
    public void testDeleteStudent_NotFound() throws Exception {
        String id = "1";
        when(studentService.deleteStudent(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/students/" + id))
                .andExpect(status().isNotFound());

        verify(studentService).deleteStudent(id);
    }
}
