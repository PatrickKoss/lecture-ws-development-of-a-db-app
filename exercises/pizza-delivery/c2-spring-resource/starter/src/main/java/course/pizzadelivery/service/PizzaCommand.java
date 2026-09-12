package course.pizzadelivery.service;

import java.math.BigDecimal;

public record PizzaCommand(
    String pizzaNumber,
    String name,
    String category,
    String ovenStation,
    BigDecimal basePrice,
    Boolean active) {}
