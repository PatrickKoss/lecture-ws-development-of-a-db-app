package com.example.restsimple.controller;

import com.example.restsimple.exception.ResourceNotFoundException;
import com.example.restsimple.model.Student;
import com.example.restsimple.repository.UpdateStudent;
import com.example.restsimple.request.StudentCreateRequest;
import com.example.restsimple.request.StudentUpdateRequest;
import com.example.restsimple.response.ErrorResponse;
import com.example.restsimple.response.StudentCreateResponse;
import com.example.restsimple.response.StudentListResponse;
import com.example.restsimple.response.StudentUpdateResponse;
import com.example.restsimple.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
}
