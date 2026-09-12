package course.pizzadelivery;

import java.math.BigDecimal;

public record Pizza(
    Long id,
    String pizzaNumber,
    String name,
    String category,
    String ovenStation,
    BigDecimal basePrice,
    Boolean active) {}
