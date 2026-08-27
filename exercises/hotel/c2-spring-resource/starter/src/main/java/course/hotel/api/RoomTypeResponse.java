package course.hotel.api;

import course.hotel.domain.RoomType;

public record RoomTypeResponse(
    Long id, String typeCode, String name, Integer capacity, Integer standardPriceCents) {
  public static RoomTypeResponse from(RoomType value) {
    return new RoomTypeResponse(
        value.id(), value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }
}
