package com.example.restsimple.adapter.in.web;

import com.example.restsimple.adapter.in.dto.CreateStudentRequest;
import com.example.restsimple.adapter.in.dto.StudentResponse;
import com.example.restsimple.adapter.in.dto.UpdateStudentRequest;
import com.example.restsimple.application.port.in.CreateStudentUseCase;
import com.example.restsimple.application.port.in.DeleteStudentUseCase;
import com.example.restsimple.application.port.in.GetStudentUseCase;
import com.example.restsimple.application.port.in.UpdateStudentUseCase;
import com.example.restsimple.domain.model.Student;
import com.example.restsimple.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    private final CreateStudentUseCase createStudentUseCase;
    private final GetStudentUseCase getStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;

    public StudentController(CreateStudentUseCase createStudentUseCase,
                           GetStudentUseCase getStudentUseCase,
                           UpdateStudentUseCase updateStudentUseCase,
                           DeleteStudentUseCase deleteStudentUseCase) {
        this.createStudentUseCase = createStudentUseCase;
        this.getStudentUseCase = getStudentUseCase;
        this.updateStudentUseCase = updateStudentUseCase;
        this.deleteStudentUseCase = deleteStudentUseCase;
    }

    @GetMapping("/healthz")
    public ErrorResponse healthz() {
        return new ErrorResponse("OK");
    }

    @GetMapping("/students")
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successfully got all students",
                    content = @Content(schema = @Schema(implementation = List.class))),
    })
    public List<StudentResponse> getAllStudents() {
        List<Student> students = getStudentUseCase.getAllStudents();
        return students.stream()
                .map(StudentResponse::fromDomain)
                .toList();
    }

    @PostMapping("/students")
    @Operation(responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created student",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody CreateStudentRequest request) {
        Student student = createStudentUseCase.createStudent(request.toCommand());
        StudentResponse response = StudentResponse.fromDomain(student);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "Successfully updated student",
                    content = @Content(schema = @Schema(implementation = StudentResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public StudentResponse updateStudent(@PathVariable String id, @Valid @RequestBody UpdateStudentRequest request) {
        Student student = updateStudentUseCase.updateStudent(request.toCommand(id));
        return StudentResponse.fromDomain(student);
    }

    @DeleteMapping("/students/{id}")
    @Operation(responses = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted student"),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> deleteStudent(@PathVariable String id) {
        deleteStudentUseCase.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}