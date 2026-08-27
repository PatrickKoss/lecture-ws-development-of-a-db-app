package course.template.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateResourceRequest(
    @NotBlank String resourceCode,
    @NotBlank String name,
    @NotNull @PositiveOrZero BigDecimal measure) {}
