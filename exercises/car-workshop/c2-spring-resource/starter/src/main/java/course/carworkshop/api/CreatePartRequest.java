package course.carworkshop.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreatePartRequest(
    @NotBlank String partNumber,
    @NotBlank String name,
    @NotBlank String category,
    @NotBlank String shelfCode,
    @NotNull @PositiveOrZero Integer stockQuantity,
    @NotNull @PositiveOrZero Integer reorderLevel,
    @NotNull @PositiveOrZero BigDecimal listPrice) {}
