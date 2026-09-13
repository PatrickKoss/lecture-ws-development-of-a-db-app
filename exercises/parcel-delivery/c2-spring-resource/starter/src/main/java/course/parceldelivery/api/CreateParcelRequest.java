package course.parceldelivery.api;

import course.parceldelivery.service.ParcelCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Pakete")
public record CreateParcelRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public ParcelCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
