package course.hotel.api;

import course.hotel.domain.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Zimmertypen")
public record RoomTypeResponse() {
  // TODO C1: Ergänzt die Response-Felder aus der Feldspezifikation im Aufgabenblatt.
  public static RoomTypeResponse from(RoomType value) {
    throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
  }
}
