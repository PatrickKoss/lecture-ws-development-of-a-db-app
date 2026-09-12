package course.hotel.domain;

public record RoomType(
    Long id, String typeCode, String name, Integer capacity, Integer standardPriceCents) {}
