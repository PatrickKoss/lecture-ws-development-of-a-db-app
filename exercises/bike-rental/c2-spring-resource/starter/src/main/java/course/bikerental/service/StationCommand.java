package course.bikerental.service;

public record StationCommand(
    String stationCode, String name, String address, Integer capacity, String status) {}
