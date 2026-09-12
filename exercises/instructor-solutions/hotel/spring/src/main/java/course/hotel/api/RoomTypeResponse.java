package course.hotel.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.hotel.domain.RoomType;

@Schema(description = "API-Darstellung der Ressource RoomType")
public record RoomTypeResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Typcode", example = "NX") String typeCode,
    @Schema(description = "Bezeichnung des Zimmertyps", example = "Next Deluxe") String name,
    @Schema(description = "Maximale Personenzahl", example = "2") Integer capacity,
    @Schema(description = "Standardpreis in Cent", example = "14900") Integer standardPriceCents) {
  public static RoomTypeResponse from(RoomType value) {
    return new RoomTypeResponse(
        value.id(), value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }
}
