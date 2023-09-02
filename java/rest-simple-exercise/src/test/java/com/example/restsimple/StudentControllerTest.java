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
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testCreateStudent() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testUpdateStudent() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testDeleteStudent() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testGetAllStudents_EmptyList() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testCreateStudent_MissingName() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testCreateStudent_MissingLastName() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testUpdateStudent_NotFound() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Test
    public void testDeleteStudent_NotFound() throws Exception {
        throw new UnsupportedOperationException("Not implemented");
    }
}
