package course.museum.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.museum.domain.Exhibit;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Exhibit")
public record ExhibitResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String inventoryCode, String title, BigDecimal insuredValue) {
  public static ExhibitResponse from(Exhibit value) {
    return new ExhibitResponse(value.id(), value.inventoryCode(), value.title(), value.insuredValue());
  }
}
