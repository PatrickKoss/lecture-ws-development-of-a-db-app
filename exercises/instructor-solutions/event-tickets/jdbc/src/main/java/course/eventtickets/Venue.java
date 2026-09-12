package course.eventtickets;

public record Venue(
    Long id,
    String venueCode,
    String name,
    String street,
    String postalCode,
    String city,
    Integer capacity) {}
