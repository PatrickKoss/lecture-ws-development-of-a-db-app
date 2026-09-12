package course.carworkshop.api;

import course.carworkshop.service.PartCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Ersatzteile")
public record UpdatePartRequest(
    @Schema(description = "Eindeutige Ersatzteilnummer", example = "P-9999")
        @NotBlank
        @Pattern(regexp = "P-\\d{4}")
        String partNumber,
    @Schema(description = "Bezeichnung", example = "Testfilter") @NotBlank @Size(min = 1, max = 150)
        String name,
    @Schema(description = "Warengruppe", example = "Filter") @NotBlank @Size(min = 1, max = 50)
        String category,
    @Schema(description = "Lagerplatz", example = "T-01") @NotBlank @Size(min = 1, max = 10)
        String shelfCode,
    @Schema(description = "Lagerbestand", example = "5") @NotNull @PositiveOrZero
        Integer stockQuantity,
    @Schema(description = "Meldebestand", example = "2") @NotNull @PositiveOrZero
        Integer reorderLevel,
    @Schema(description = "Listenpreis", example = "14.90") @NotNull @PositiveOrZero
        BigDecimal listPrice) {
  public PartCommand toCommand() {
    return new PartCommand(
        partNumber, name, category, shelfCode, stockQuantity, reorderLevel, listPrice);
  }
}
