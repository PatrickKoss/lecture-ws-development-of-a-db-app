package course.pizzadelivery.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.pizzadelivery.domain.Pizza;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Pizza")
public record PizzaResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String pizzaNumber,
    String name,
    String category,
    String ovenStation,
    BigDecimal basePrice,
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
