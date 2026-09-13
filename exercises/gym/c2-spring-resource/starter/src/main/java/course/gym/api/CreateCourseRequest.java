package course.gym.api;

import course.gym.service.CourseCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Kurse")
public record CreateCourseRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public CourseCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
