package com.example.restsimple.exception;

import com.example.restsimple.domain.exception.AdminNotFoundException;
import com.example.restsimple.domain.exception.AuthenticationException;
import com.example.restsimple.domain.exception.InvalidStudentDataException;
import com.example.restsimple.domain.exception.InvalidTokenException;
import com.example.restsimple.domain.exception.StudentNotFoundException;
import com.example.restsimple.response.ErrorResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fields = new LinkedHashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(error.getField(), error.getDefaultMessage());
        }
        return response(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "The request contains invalid fields", fields);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "STUDENT_NOT_FOUND", ex.getMessage());
    }

    @ExceptionHandler(InvalidStudentDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidStudentDataException(InvalidStudentDataException ex) {
        return response(HttpStatus.BAD_REQUEST, "INVALID_STUDENT", ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return response(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex) {
        return response(HttpStatus.UNAUTHORIZED, "AUTHENTICATION_FAILED", "Authentication failed");
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenException(InvalidTokenException ex) {
        return response(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "Invalid or expired token");
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAdminNotFoundException(AdminNotFoundException ex) {
        return response(HttpStatus.NOT_FOUND, "ADMIN_NOT_FOUND", "Admin not found");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        log.error("Unhandled request error", ex);
        return response(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "An unexpected error occurred");
    }

    private ResponseEntity<ErrorResponse> response(HttpStatus status, String code, String message) {
        return response(status, code, message, Map.of());
    }

    private ResponseEntity<ErrorResponse> response(
            HttpStatus status,
            String code,
            String message,
            Map<String, String> fields) {
        ErrorResponse body = new ErrorResponse(code, message, MDC.get("correlationId"), fields);
        return ResponseEntity.status(status).body(body);
    }
}
