package course.bikerental.api;

import course.bikerental.service.StationCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Stationen")
public record CreateStationRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public StationCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
