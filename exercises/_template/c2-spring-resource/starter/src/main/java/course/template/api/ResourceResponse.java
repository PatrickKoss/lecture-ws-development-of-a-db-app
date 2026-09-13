package course.template.api;

import course.template.domain.Resource;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Ressourcen")
public record ResourceResponse() {
  // TODO C1: Ergänzt die Response-Felder aus der Feldspezifikation im Aufgabenblatt.
  public static ResourceResponse from(Resource value) {
    throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
  }
}
