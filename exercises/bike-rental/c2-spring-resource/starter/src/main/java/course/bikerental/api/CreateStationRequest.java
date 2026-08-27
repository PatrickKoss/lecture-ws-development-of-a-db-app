package course.bikerental.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateStationRequest(
    @NotBlank String stationCode,
    @NotBlank String name,
    @NotBlank String address,
    @NotNull @PositiveOrZero Integer capacity,
    @NotBlank String status) {}
