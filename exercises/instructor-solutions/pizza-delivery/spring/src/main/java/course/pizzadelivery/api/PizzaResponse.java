package course.pizzadelivery.api;

import course.pizzadelivery.domain.Pizza;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Pizzen")
public record PizzaResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutige Pizzanummer",
            example = "P-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String pizzaNumber,
    @Schema(
            description = "Name der Pizza",
            example = "Testpizza",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Kategorie der Speisekarte",
            example = "Saisonal",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String category,
    @Schema(
            description = "Zuständige Ofenstation",
            example = "OFEN-Z",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String ovenStation,
    @Schema(
            description = "Grundpreis",
            example = "11.50",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal basePrice,
    @Schema(
            description = "Gibt an, ob die Pizza bestellbar ist",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean active) {
  public static PizzaResponse from(Pizza value) {
    return new PizzaResponse(
        value.id(),
        value.pizzaNumber(),
        value.name(),
        value.category(),
        value.ovenStation(),
        value.basePrice(),
        value.active());
  }
}
