package course.pizzadelivery.api;

import course.pizzadelivery.domain.Pizza;
import java.math.BigDecimal;

public record PizzaResponse(
    Long id,
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
