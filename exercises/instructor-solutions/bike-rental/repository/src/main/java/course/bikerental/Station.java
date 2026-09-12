package course.bikerental;

public record Station(
    Long id, String stationCode, String name, String address, Integer capacity, String status) {}
