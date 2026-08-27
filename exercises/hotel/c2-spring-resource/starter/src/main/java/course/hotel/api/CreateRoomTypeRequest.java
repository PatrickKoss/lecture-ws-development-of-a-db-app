package course.hotel.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateRoomTypeRequest(
    @NotBlank String typeCode,
    @NotBlank String name,
    @NotNull @PositiveOrZero Integer capacity,
    @NotNull @PositiveOrZero Integer standardPriceCents) {}
