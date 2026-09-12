package course.parceldelivery.api;

import course.parceldelivery.service.ParcelCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Pakete")
public record UpdateParcelRequest(
    @Schema(description = "Eindeutiger Sendungscode", example = "PK-99")
        @NotBlank
        @Size(min = 1, max = 40)
        String trackingCode,
    @Schema(description = "Name des Empfängers", example = "Test Empfänger")
        @NotBlank
        @Size(min = 1, max = 160)
        String recipient,
    @Schema(description = "Gewicht in Kilogramm", example = "3.5")
        @NotNull
        @PositiveOrZero
        @DecimalMax("31.5")
        BigDecimal weight) {
  public ParcelCommand toCommand() {
    return new ParcelCommand(trackingCode, recipient, weight);
  }
}
