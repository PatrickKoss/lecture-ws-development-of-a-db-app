package course.museum.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.museum.domain.Exhibit;
import java.math.BigDecimal;

@Schema(description = "API-Darstellung der Ressource Exhibit")
public record ExhibitResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige Inventarnummer", example = "EX-99") String inventoryCode,
    @Schema(description = "Titel des Exponats", example = "Stadtansicht") String title,
    @Schema(description = "Versicherungswert in Euro", example = "25000.00") BigDecimal insuredValue) {
  public static ExhibitResponse from(Exhibit value) {
    return new ExhibitResponse(value.id(), value.inventoryCode(), value.title(), value.insuredValue());
  }
}
