package course.parceldelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabedaten für die Ressource Parcel")
public record CreateParcelRequest(
    @Schema(description = "Eindeutiger Sendungscode", example = "PK-99")
    @NotBlank String trackingCode,
    @Schema(description = "Name des Empfängers", example = "Erika Muster")
    @NotBlank String recipient,
    @Schema(description = "Gewicht in Kilogramm", example = "2.50")
    @NotNull @PositiveOrZero BigDecimal weight
) {}
