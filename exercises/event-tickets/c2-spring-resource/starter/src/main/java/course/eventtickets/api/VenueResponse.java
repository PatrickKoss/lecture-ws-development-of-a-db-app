package course.eventtickets.api;

import course.eventtickets.domain.Venue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Spielorte")
public record VenueResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Spielortcode",
            example = "V-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String venueCode,
    @Schema(
            description = "Name des Spielorts",
            example = "Testhalle",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Straße und Hausnummer",
            example = "Testweg 1",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String street,
    @Schema(
            description = "Postleitzahl",
            example = "45127",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String postalCode,
    @Schema(description = "Ort", example = "Essen", requiredMode = Schema.RequiredMode.REQUIRED)
        String city,
    @Schema(
            description = "Maximale Besucherzahl",
            example = "300",
            requiredMode = Schema.RequiredMode.REQUIRED)
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
