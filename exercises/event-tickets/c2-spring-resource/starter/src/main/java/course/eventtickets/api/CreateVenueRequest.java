package course.eventtickets.api;

import course.eventtickets.service.VenueCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Spielorte")
public record CreateVenueRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public VenueCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
