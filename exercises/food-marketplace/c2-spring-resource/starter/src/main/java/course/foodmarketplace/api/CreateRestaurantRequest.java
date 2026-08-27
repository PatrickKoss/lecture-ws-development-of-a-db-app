package course.foodmarketplace.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateRestaurantRequest(
    @NotBlank String partnerNumber,
    @NotBlank String name,
    @NotBlank String street,
    @NotBlank String postalCode,
    @NotBlank String city,
    @NotNull @PositiveOrZero BigDecimal commissionRate,
    @NotNull Boolean active) {}
