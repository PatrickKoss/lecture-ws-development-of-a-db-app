package course.cinema.api;

import course.cinema.service.MovieCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Filme")
public record CreateMovieRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public MovieCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
