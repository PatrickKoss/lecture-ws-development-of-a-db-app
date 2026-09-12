package course.pizzadelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.pizzadelivery.domain.Pizza;
import java.math.BigDecimal;

@Schema(description = "API-Darstellung der Ressource Pizza")
public record PizzaResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige Pizzanummer", example = "P-99") String pizzaNumber,
    @Schema(description = "Name der Pizza", example = "Margherita Speciale") String name,
    @Schema(description = "Produktkategorie", example = "CLASSIC") String category,
    @Schema(description = "Zugeordnete Ofenstation", example = "OVEN-2") String ovenStation,
    @Schema(description = "Grundpreis in Euro", example = "11.90") BigDecimal basePrice,
    @Schema(description = "Kennzeichnet ein bestellbares Produkt", example = "true") Boolean active) {
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
