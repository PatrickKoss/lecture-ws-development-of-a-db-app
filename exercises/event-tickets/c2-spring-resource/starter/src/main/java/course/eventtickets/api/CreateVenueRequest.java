package course.eventtickets.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateVenueRequest(
    @NotBlank String venueCode,
    @NotBlank String name,
    @NotBlank String street,
    @NotBlank String postalCode,
    @NotBlank String city,
    @NotNull @PositiveOrZero Integer capacity) {}
