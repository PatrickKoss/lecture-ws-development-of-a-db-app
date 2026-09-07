package course.hotel.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.hotel.domain.RoomType;

@Schema(description = "Öffentliche Darstellung der Ressource RoomType")
public record RoomTypeResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String typeCode, String name, Integer capacity, Integer standardPriceCents) {
  public static RoomTypeResponse from(RoomType value) {
    return new RoomTypeResponse(
        value.id(), value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }
}
