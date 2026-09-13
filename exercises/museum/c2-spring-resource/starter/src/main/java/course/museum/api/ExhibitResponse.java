package course.museum.api;

import course.museum.domain.Exhibit;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Exponate")
public record ExhibitResponse() {
  // TODO C1: Ergänzt die Response-Felder aus der Feldspezifikation im Aufgabenblatt.
  public static ExhibitResponse from(Exhibit value) {
    throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
  }
}
