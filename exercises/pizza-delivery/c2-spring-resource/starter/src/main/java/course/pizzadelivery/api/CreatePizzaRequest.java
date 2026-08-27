package course.pizzadelivery.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreatePizzaRequest(
    @NotBlank String pizzaNumber,
    @NotBlank String name,
    @NotBlank String category,
    @NotBlank String ovenStation,
    @NotNull @PositiveOrZero BigDecimal basePrice,
    @NotNull Boolean active) {}
