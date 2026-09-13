package course.carworkshop.api;

import course.carworkshop.service.PartCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Ersatzteile")
public record CreatePartRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public PartCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
