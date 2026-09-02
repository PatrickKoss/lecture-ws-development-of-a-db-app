package course.museum.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateExhibitRequest(
    @NotBlank String inventoryCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero BigDecimal insuredValue) {}
