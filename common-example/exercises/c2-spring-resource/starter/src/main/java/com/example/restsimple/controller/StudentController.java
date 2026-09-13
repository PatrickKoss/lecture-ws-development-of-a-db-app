package com.example.restsimple.controller;

import com.example.restsimple.dto.CreateStudentRequest;
import com.example.restsimple.dto.StudentResponse;
import com.example.restsimple.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Studierende")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Alle Studierenden lesen")
    public List<StudentResponse> findAll() {
        throw new UnsupportedOperationException("TODO C2: Liste über Service und Mapping liefern");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Eine Studentin oder einen Studenten lesen")
    public StudentResponse findById(@PathVariable long id) {
        throw new UnsupportedOperationException("TODO C2: Einzelressource über Service und Mapping liefern");
    }

    @PostMapping
    @Operation(summary = "Studierende anlegen")
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody CreateStudentRequest request) {
        throw new UnsupportedOperationException("TODO C3: POST mit 201 und Location implementieren");
    }

    @GetMapping("/health")
    @Operation(summary = "Einfacher Health Check für die erste Inbetriebnahme")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "university-backend");
    }
}
