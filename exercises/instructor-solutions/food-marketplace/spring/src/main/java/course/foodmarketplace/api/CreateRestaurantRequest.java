package course.foodmarketplace.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabedaten für die Ressource Restaurant")
public record CreateRestaurantRequest(
    @Schema(description = "Eindeutige Partnernummer", example = "R-199")
    @NotBlank String partnerNumber,
    @Schema(description = "Name des Restaurants", example = "Pasta Haus")
    @NotBlank String name,
    @Schema(description = "Straße und Hausnummer", example = "Marktstraße 8")
    @NotBlank String street,
    @Schema(description = "Postleitzahl", example = "50667")
    @NotBlank String postalCode,
    @Schema(description = "Ort", example = "Köln")
    @NotBlank String city,
    @Schema(description = "Provisionssatz in Prozent", example = "12.50")
    @NotNull @PositiveOrZero BigDecimal commissionRate,
    @Schema(description = "Kennzeichnet einen aktiven Partner", example = "true")
    @NotNull Boolean active
) {}
