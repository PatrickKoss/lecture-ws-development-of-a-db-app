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

    @get:Operation(
        responses = [ApiResponse(
            responseCode = "200",
            description = "Successfully got all students",
            content = arrayOf(
                Content(
                    schema = Schema(implementation = StudentListResponse::class)
                )
            )
        )]
    )
    @get:GetMapping("/students")
    val allStudents: StudentListResponse
        get() {
            val students = studentService.allStudents
            return StudentListResponse(students)
        }

    @PostMapping("/students")
    @Operation(
        responses = [ApiResponse(
            responseCode = "201",
            description = "Successfully created student",
            content = arrayOf(Content(schema = Schema(implementation = StudentCreateResponse::class)))
        ), ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = arrayOf(Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class)))
        )]
    )
    @ResponseStatus(value = HttpStatus.CREATED)
    fun createStudent(@RequestBody student: @Valid StudentCreateRequest?): ResponseEntity<StudentCreateResponse> {
        val uuid = UUID.randomUUID().toString()
        val createdOn = LocalDateTime.now()

        val studentToCreate = Student(uuid, student?.name, student?.lastName, createdOn)
        val studentCreated = studentService.createStudent(studentToCreate)

        return ResponseEntity(StudentCreateResponse(studentCreated), HttpStatus.CREATED)
    }

    @PutMapping("/students/{id}")
    @Operation(
        responses = [ApiResponse(
            responseCode = "200",
            description = "Successfully updated student",
            content = arrayOf(Content(schema = Schema(implementation = StudentUpdateResponse::class)))
        ), ApiResponse(
            responseCode = "400",
            description = "Bad Request",
            content = arrayOf(Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class)))
        ), ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = arrayOf(Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class)))
        )]
    )
    fun updateStudent(
        @PathVariable id: String,
        @RequestBody updatedStudent: StudentUpdateRequest
    ): StudentUpdateResponse {
        val student = studentService.updateStudent(id, UpdateStudent(updatedStudent.name, updatedStudent.lastName))
            .orElseThrow { ResourceNotFoundException("Student not found with id $id") }
        return StudentUpdateResponse(student)
    }

    @DeleteMapping("/students/{id}")
    @Operation(
        responses = [ApiResponse(
            responseCode = "204",
            description = "Successfully deleted student"
        ), ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = arrayOf(Content(mediaType = "application/json", schema = Schema(implementation = ErrorResponse::class)))
        )]
    )
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    fun deleteStudent(@PathVariable id: String): ResponseEntity<*> {
        studentService.deleteStudent(id)
            .orElseThrow { ResourceNotFoundException("Student not found with id $id") }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build<Any>()
    }
}
