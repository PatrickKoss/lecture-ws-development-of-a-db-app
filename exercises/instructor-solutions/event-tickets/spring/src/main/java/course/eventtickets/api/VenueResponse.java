package course.eventtickets.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.eventtickets.domain.Venue;

@Schema(description = "API-Darstellung der Ressource Venue")
public record VenueResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Spielortcode", example = "V-99") String venueCode,
    @Schema(description = "Name des Spielorts", example = "Stadthalle") String name,
    @Schema(description = "Straße und Hausnummer", example = "Hauptstraße 1") String street,
    @Schema(description = "Postleitzahl", example = "10115") String postalCode,
    @Schema(description = "Ort", example = "Berlin") String city,
    @Schema(description = "Maximale Besucherzahl", example = "1200") Integer capacity) {
  public static VenueResponse from(Venue value) {
    return new VenueResponse(
        value.id(),
        value.venueCode(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.capacity());
  }
}
