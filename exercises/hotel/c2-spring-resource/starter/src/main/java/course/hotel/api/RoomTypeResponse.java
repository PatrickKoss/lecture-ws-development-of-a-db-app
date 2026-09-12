package course.hotel.api;

import course.hotel.domain.RoomType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Zimmertypen")
public record RoomTypeResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Typcode",
            example = "NX",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String typeCode,
    @Schema(
            description = "Name des Zimmertyps",
            example = "Testzimmer",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Maximale Belegung",
            example = "2",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer capacity,
    @Schema(
            description = "Standardpreis in Cent",
            example = "14900",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer standardPriceCents) {
  public static RoomTypeResponse from(RoomType value) {
    return new RoomTypeResponse(
        value.id(), value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }
}
