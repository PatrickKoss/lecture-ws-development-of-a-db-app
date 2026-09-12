package course.carworkshop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.carworkshop.domain.Part;
import java.math.BigDecimal;

@Schema(description = "API-Darstellung der Ressource Part")
public record PartResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige Teilenummer", example = "P-1999") String partNumber,
    @Schema(description = "Bezeichnung des Ersatzteils", example = "Ölfilter") String name,
    @Schema(description = "Teilekategorie", example = "Filter") String category,
    @Schema(description = "Lagerplatz", example = "R-12") String shelfCode,
    @Schema(description = "Aktueller Lagerbestand", example = "25") Integer stockQuantity,
    @Schema(description = "Meldebestand", example = "5") Integer reorderLevel,
    @Schema(description = "Listenpreis in Euro", example = "19.90") BigDecimal listPrice) {
  public static PartResponse from(Part value) {
    return new PartResponse(
        value.id(),
        value.partNumber(),
        value.name(),
        value.category(),
        value.shelfCode(),
        value.stockQuantity(),
        value.reorderLevel(),
        value.listPrice());
  }
}
