package course.museum.api;

import course.museum.service.ExhibitCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Exponate")
public record CreateExhibitRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public ExhibitCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
