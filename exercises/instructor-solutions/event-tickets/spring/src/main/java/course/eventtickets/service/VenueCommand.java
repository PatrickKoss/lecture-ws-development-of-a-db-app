package course.eventtickets.service;

public record VenueCommand(
    String venueCode,
    String name,
    String street,
    String postalCode,
    String city,
    Integer capacity) {}
