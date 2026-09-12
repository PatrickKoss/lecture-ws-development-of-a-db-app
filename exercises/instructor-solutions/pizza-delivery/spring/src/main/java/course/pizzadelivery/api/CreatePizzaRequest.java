package course.pizzadelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabedaten für die Ressource Pizza")
public record CreatePizzaRequest(
    @Schema(description = "Eindeutige Pizzanummer", example = "P-99")
    @NotBlank String pizzaNumber,
    @Schema(description = "Name der Pizza", example = "Margherita Speciale")
    @NotBlank String name,
    @Schema(description = "Produktkategorie", example = "CLASSIC")
    @NotBlank String category,
    @Schema(description = "Zugeordnete Ofenstation", example = "OVEN-2")
    @NotBlank String ovenStation,
    @Schema(description = "Grundpreis in Euro", example = "11.90")
    @NotNull @PositiveOrZero BigDecimal basePrice,
    @Schema(description = "Kennzeichnet ein bestellbares Produkt", example = "true")
    @NotNull Boolean active
) {}
