package course.bikerental.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.bikerental.domain.Station;

@Schema(description = "Öffentliche Darstellung der Ressource Station")
public record StationResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String stationCode, String name, String address, Integer capacity, String status) {
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
