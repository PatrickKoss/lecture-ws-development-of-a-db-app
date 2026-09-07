package course.foodmarketplace.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Restaurant")
public record CreateRestaurantRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "R-199")
    @NotBlank String partnerNumber,
    @NotBlank String name,
    @NotBlank String street,
    @NotBlank String postalCode,
    @NotBlank String city,
    @NotNull @PositiveOrZero BigDecimal commissionRate,
    @NotNull Boolean active) {}
