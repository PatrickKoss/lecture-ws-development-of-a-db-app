package course.bikerental.api;

import course.bikerental.domain.Station;

public record StationResponse(
    Long id, String stationCode, String name, String address, Integer capacity, String status) {
  public static StationResponse from(Station value) {
    return new StationResponse(
        value.id(),
        value.stationCode(),
        value.name(),
        value.address(),
        value.capacity(),
        value.status());
  }
}
