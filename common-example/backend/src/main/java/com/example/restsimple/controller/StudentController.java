package com.example.restsimple.controller;

import com.example.restsimple.dto.CreateStudentRequest;
import com.example.restsimple.dto.StudentResponse;
import com.example.restsimple.service.StudentService;
import com.example.restsimple.response.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/api/students", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Studierende")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Alle Studierenden lesen")
    @ApiResponse(responseCode = "200", description = "Nach ID sortierte Liste",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentResponse.class))))
    public List<StudentResponse> findAll() {
        return service.findAll().stream().map(StudentResponse::from).toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Eine Studentin oder einen Studenten lesen")
    @ApiResponse(responseCode = "200", description = "Ressource gefunden",
            content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    @ApiResponse(responseCode = "400", description = "ID ist keine positive Zahl",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "404", description = "ID ist unbekannt",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public StudentResponse findById(@PathVariable @Positive long id) {
        return StudentResponse.from(service.findById(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Studierende anlegen")
    @ApiResponse(responseCode = "201", description = "Ressource angelegt",
            headers = @Header(name = "Location", description = "URL der neuen Ressource",
                    schema = @Schema(type = "string", format = "uri")),
            content = @Content(schema = @Schema(implementation = StudentResponse.class)))
    @ApiResponse(responseCode = "400", description = "Eingabe oder JSON ist ungültig",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    @ApiResponse(responseCode = "409", description = "Matrikelnummer oder E-Mail ist vergeben",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<StudentResponse> create(@Valid @RequestBody CreateStudentRequest request) {
        StudentResponse response = StudentResponse.from(service.create(request.toCommand()));
        return ResponseEntity.created(URI.create("/api/students/" + response.id())).body(response);
    }

    @GetMapping("/health")
    @Operation(summary = "Einfacher Health Check für die erste Inbetriebnahme")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "university-backend");
    }
}
