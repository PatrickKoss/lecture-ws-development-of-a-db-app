package course.parceldelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Parcel")
public record CreateParcelRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "PK-99")
    @NotBlank String trackingCode,
    @NotBlank String recipient,
    @NotNull @PositiveOrZero BigDecimal weight) {}
