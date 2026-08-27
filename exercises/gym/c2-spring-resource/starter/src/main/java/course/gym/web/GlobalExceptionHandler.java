package course.gym.web;

import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private String correlationId() {
    return String.valueOf(MDC.get("correlationId"));
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ApiError notFound(NotFoundException ex) {
    return new ApiError(ex.code(), ex.getMessage(), correlationId(), Map.of());
  }

  @ExceptionHandler(ConflictException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ApiError conflict(ConflictException ex) {
    return new ApiError(ex.code(), ex.getMessage(), correlationId(), Map.of());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ApiError validation(MethodArgumentNotValidException ex) {
    var fields =
        ex.getBindingResult().getFieldErrors().stream()
            .collect(
                Collectors.toMap(
                    error -> error.getField(),
                    error -> String.valueOf(error.getDefaultMessage()),
                    (a, b) -> a));
    return new ApiError("VALIDATION_FAILED", "Eingabe ist ungültig", correlationId(), fields);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  ApiError unexpected(Exception ex) {
    LOG.error("Unhandled request error", ex);
    return new ApiError("INTERNAL_ERROR", "Interner Fehler", correlationId(), Map.of());
  }
}
