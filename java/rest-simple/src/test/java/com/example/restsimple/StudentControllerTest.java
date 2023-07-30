package com.example.restsimple;

import com.example.restsimple.controller.StudentController;
import com.example.restsimple.model.Student;
import com.example.restsimple.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("id", "mnr", "name", "lastName", LocalDateTime.now());
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(student);

        when(studentRepository.findAll()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testCreateStudent() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student("id", "newMnr", "newName", "newLastName", LocalDateTime.now());

        when(studentRepository.findById(any(String.class))).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).findById(any(String.class));
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentRepository).deleteById(any(String.class));
        when(studentRepository.existsById(any(String.class))).thenReturn(true);

        mockMvc.perform(delete("/students/{id}", student.getId()))
                .andExpect(status().isOk());

        verify(studentRepository, times(1)).existsById(any(String.class));
        verify(studentRepository, times(1)).deleteById(any(String.class));
    }
}
