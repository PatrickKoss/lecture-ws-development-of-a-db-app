package com.example.restsimple.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "Student management operations")
public class StudentController {

    @GetMapping("/health")
    @Operation(
        summary = "Health check endpoint", 
        description = "Returns the health status of the student service"
    )
    @ApiResponse(responseCode = "200", description = "Service is healthy")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "student-service",
            "timestamp", LocalDateTime.now()
        ));
    }
}
