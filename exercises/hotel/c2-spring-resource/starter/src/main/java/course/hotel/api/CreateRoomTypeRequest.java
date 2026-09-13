package course.hotel.api;

import course.hotel.service.RoomTypeCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Zimmertypen")
public record CreateRoomTypeRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public RoomTypeCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
