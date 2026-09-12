package course.museum.api;

import course.museum.service.ExhibitCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Exponate")
public record UpdateExhibitRequest(
    @Schema(description = "Eindeutiger Inventarcode", example = "EX-99")
        @NotBlank
        @Size(min = 1, max = 40)
        String inventoryCode,
    @Schema(description = "Titel des Exponats", example = "Testexponat")
        @NotBlank
        @Size(min = 1, max = 200)
        String title,
    @Schema(description = "Versicherungswert", example = "1000.00") @NotNull @PositiveOrZero
        BigDecimal insuredValue) {
  public ExhibitCommand toCommand() {
    return new ExhibitCommand(inventoryCode, title, insuredValue);
  }
}
