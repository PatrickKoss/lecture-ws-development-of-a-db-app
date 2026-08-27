package course.bikerental.domain;

public record Station(
    Long id, String stationCode, String name, String address, Integer capacity, String status) {}
