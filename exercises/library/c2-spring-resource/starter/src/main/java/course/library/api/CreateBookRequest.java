package course.library.api;

import course.library.service.BookCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Bücher")
public record CreateBookRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public BookCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
