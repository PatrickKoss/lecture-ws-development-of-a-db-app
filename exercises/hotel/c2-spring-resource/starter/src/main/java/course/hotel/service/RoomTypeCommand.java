package course.hotel.service;

public record RoomTypeCommand(
    String typeCode, String name, Integer capacity, Integer standardPriceCents) {}
