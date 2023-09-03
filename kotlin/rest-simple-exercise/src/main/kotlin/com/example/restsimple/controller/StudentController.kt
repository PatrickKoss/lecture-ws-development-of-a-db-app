package com.example.restsimple.controller

import com.example.restsimple.exception.ResourceNotFoundException
import com.example.restsimple.model.Student
import com.example.restsimple.repository.UpdateStudent
import com.example.restsimple.request.StudentCreateRequest
import com.example.restsimple.request.StudentUpdateRequest
import com.example.restsimple.response.ErrorResponse
import com.example.restsimple.response.StudentCreateResponse
import com.example.restsimple.response.StudentListResponse
import com.example.restsimple.response.StudentUpdateResponse
import com.example.restsimple.service.StudentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.util.*
import javax.validation.Valid

@RestController
class StudentController(private val studentService: StudentService) {
    @GetMapping("/healthz")
    fun healthz(): ErrorResponse {
        return ErrorResponse("OK")
    }
}
