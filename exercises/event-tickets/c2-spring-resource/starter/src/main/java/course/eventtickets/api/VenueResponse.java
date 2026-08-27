package course.eventtickets.api;

import course.eventtickets.domain.Venue;

public record VenueResponse(
    Long id,
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
