package course.carworkshop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabedaten für die Ressource Part")
public record CreatePartRequest(
    @Schema(description = "Eindeutige Teilenummer", example = "P-1999")
    @NotBlank String partNumber,
    @Schema(description = "Bezeichnung des Ersatzteils", example = "Ölfilter")
    @NotBlank String name,
    @Schema(description = "Teilekategorie", example = "Filter")
    @NotBlank String category,
    @Schema(description = "Lagerplatz", example = "R-12")
    @NotBlank String shelfCode,
    @Schema(description = "Aktueller Lagerbestand", example = "25")
    @NotNull @PositiveOrZero Integer stockQuantity,
    @Schema(description = "Meldebestand", example = "5")
    @NotNull @PositiveOrZero Integer reorderLevel,
    @Schema(description = "Listenpreis in Euro", example = "19.90")
    @NotNull @PositiveOrZero BigDecimal listPrice
) {}
