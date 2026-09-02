package com.example.restsimple.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.restsimple.domain.Student;
import com.example.restsimple.service.StudentService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("Nach der gemeinsamen Implementierung von StudentService aktivieren")
@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired MockMvc mockMvc;
    @MockitoBean StudentService service;

    @Test
    void returnsStudentsUsingTheHttpContract() throws Exception {
        when(service.findAll()).thenReturn(List.of(
                student(1L, "Ada", "Lovelace", "M2026001"),
                student(2L, "Alan", "Turing", "M2026002")));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ada"))
                .andExpect(jsonPath("$[1].firstName").value("Alan"));
    }

    private static Student student(Long id, String firstName, String lastName, String number) {
        return new Student(
                id,
                firstName,
                lastName,
                firstName.toLowerCase() + "@hs.example",
                number,
                LocalDate.of(2026, 4, 1));
    }
}
