package course.parceldelivery.web;

import course.parceldelivery.repository.PersistenceConstraintException;
import course.parceldelivery.service.ResourceConflictException;
import course.parceldelivery.service.ResourceNotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ApiError notFound(ResourceNotFoundException ex) {
    return error(ex.code(), ex.getMessage());
  }

  @ExceptionHandler(ResourceConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ApiError conflict(ResourceConflictException ex) {
    return error(ex.code(), ex.getMessage());
  }

  @ExceptionHandler(PersistenceConstraintException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ApiError persistenceConflict(PersistenceConstraintException ex) {
    LOG.warn("Database constraint rejected a request", ex);
    return error("DATA_INTEGRITY_VIOLATION", "Die Änderung verletzt eine Datenbankregel");
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError validation(MethodArgumentNotValidException ex) {
    var fields =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    field -> field.getField(),
                    field -> String.valueOf(field.getDefaultMessage()),
                    (a, b) -> a));
    return new ApiError("VALIDATION_FAILED", "Eingabe ist ungültig", correlationId(), fields);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError constraintValidation(ConstraintViolationException ex) {
    return error("VALIDATION_FAILED", "Eingabe ist ungültig");
  }

  @ExceptionHandler({
    MethodArgumentTypeMismatchException.class,
    HandlerMethodValidationException.class
  })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError requestParameter(Exception ex) {
    return error("INVALID_REQUEST_PARAMETER", "Pfad- oder Anfrageparameter ist ungültig");
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError malformedJson(HttpMessageNotReadableException ex) {
    return error("MALFORMED_JSON", "JSON konnte nicht gelesen werden");
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
  ApiError unsupportedMediaType(HttpMediaTypeNotSupportedException ex) {
    return error("UNSUPPORTED_MEDIA_TYPE", "Content-Type muss application/json sein");
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  ResponseEntity<ApiError> methodNotAllowed(HttpRequestMethodNotSupportedException ex) {
    var allow = ex.getSupportedMethods() == null ? "" : String.join(", ", ex.getSupportedMethods());
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
        .header(HttpHeaders.ALLOW, allow)
        .body(error("METHOD_NOT_ALLOWED", "HTTP-Methode wird für diese URL nicht unterstützt"));
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  ApiError notAcceptable(HttpMediaTypeNotAcceptableException ex) {
    return error("NOT_ACCEPTABLE", "Die angeforderte Repräsentation ist nicht verfügbar");
  }

  @ExceptionHandler(NoResourceFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ApiError routeNotFound(NoResourceFoundException ex) {
    return error("ROUTE_NOT_FOUND", "URL ist unbekannt");
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ApiError> unexpected(Exception ex) {
    LOG.error("Unhandled request error", ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(error("INTERNAL_ERROR", "Interner Fehler"));
  }

  private ApiError error(String code, String message) {
    return new ApiError(code, message, correlationId(), Map.of());
  }

  private String correlationId() {
    return MDC.get("correlationId") == null ? "unknown" : MDC.get("correlationId");
  }
}
