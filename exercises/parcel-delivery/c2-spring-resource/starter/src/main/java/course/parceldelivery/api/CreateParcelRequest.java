package course.parceldelivery.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateParcelRequest(
    @NotBlank String trackingCode,
    @NotBlank String recipient,
    @NotNull @PositiveOrZero BigDecimal weight) {}
