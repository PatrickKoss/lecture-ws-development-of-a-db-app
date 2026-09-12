package course.carworkshop.api;

import course.carworkshop.domain.Part;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Ersatzteile")
public record PartResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutige Ersatzteilnummer",
            example = "P-9999",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String partNumber,
    @Schema(
            description = "Bezeichnung",
            example = "Testfilter",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Warengruppe",
            example = "Filter",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String category,
    @Schema(
            description = "Lagerplatz",
            example = "T-01",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String shelfCode,
    @Schema(
            description = "Lagerbestand",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer stockQuantity,
    @Schema(
            description = "Meldebestand",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer reorderLevel,
    @Schema(
            description = "Listenpreis",
            example = "14.90",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal listPrice) {
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
