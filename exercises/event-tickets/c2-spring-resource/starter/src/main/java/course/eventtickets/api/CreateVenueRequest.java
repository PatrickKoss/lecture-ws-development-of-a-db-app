package course.eventtickets.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource Venue")
public record CreateVenueRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "V-99")
    @NotBlank String venueCode,
    @NotBlank String name,
    @NotBlank String street,
    @NotBlank String postalCode,
    @NotBlank String city,
    @NotNull @PositiveOrZero Integer capacity) {}
