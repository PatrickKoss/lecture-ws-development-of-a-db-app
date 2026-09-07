package course.pizzadelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Pizza")
public record CreatePizzaRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "P-99")
    @NotBlank String pizzaNumber,
    @NotBlank String name,
    @NotBlank String category,
    @NotBlank String ovenStation,
    @NotNull @PositiveOrZero BigDecimal basePrice,
    @NotNull Boolean active) {}
