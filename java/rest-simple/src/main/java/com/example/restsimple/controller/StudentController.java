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

    @GetMapping("/healthz")
    public ErrorResponse healthz() {
        return new ErrorResponse("OK");
    }

    @GetMapping("/students")
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successfully got all students",
                    content = @Content(schema = @Schema(implementation = StudentListResponse.class))),
    })
    public StudentListResponse getAllStudents() {
        List<Student> students = studentService.getAllStudents();

        return new StudentListResponse(students);
    }

    @PostMapping("/students")
    @Operation(responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created student",
                    content = @Content(schema = @Schema(implementation = StudentCreateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<StudentCreateResponse> createStudent(@Valid @RequestBody StudentCreateRequest student) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime createdOn = LocalDateTime.now();

        Student studentToCreate = new Student(uuid, student.getName(), student.getLastName(), createdOn);
        Student studentCreated = studentService.createStudent(studentToCreate);

        return new ResponseEntity<>(new StudentCreateResponse(studentCreated), HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated student",
                    content = @Content(schema = @Schema(implementation = StudentUpdateResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public StudentUpdateResponse updateStudent(@Valid @PathVariable String id, @RequestBody StudentUpdateRequest updatedStudent) {
        Student student = studentService.updateStudent(id, new UpdateStudent(updatedStudent.getName(), updatedStudent.getLastName()))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        return new StudentUpdateResponse(student);
    }

    @DeleteMapping("/students/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted student"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
