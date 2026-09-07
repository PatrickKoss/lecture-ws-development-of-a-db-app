package course.eventtickets.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.eventtickets.domain.Venue;

@Schema(description = "Öffentliche Darstellung der Ressource Venue")
public record VenueResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String venueCode,
    String name,
    String street,
    String postalCode,
    String city,
    Integer capacity) {
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
