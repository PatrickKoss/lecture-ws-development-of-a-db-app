package course.eventtickets.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource Venue")
public record CreateVenueRequest(
    @Schema(description = "Eindeutiger Spielortcode", example = "V-99")
    @NotBlank String venueCode,
    @Schema(description = "Name des Spielorts", example = "Stadthalle")
    @NotBlank String name,
    @Schema(description = "Straße und Hausnummer", example = "Hauptstraße 1")
    @NotBlank String street,
    @Schema(description = "Postleitzahl", example = "10115")
    @NotBlank String postalCode,
    @Schema(description = "Ort", example = "Berlin")
    @NotBlank String city,
    @Schema(description = "Maximale Besucherzahl", example = "1200")
    @NotNull @PositiveOrZero Integer capacity
) {}
