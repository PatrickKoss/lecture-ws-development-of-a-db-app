package course.bikerental.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.bikerental.domain.Station;

@Schema(description = "API-Darstellung der Ressource Station")
public record StationResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Stationscode", example = "MS-NEW") String stationCode,
    @Schema(description = "Name der Station", example = "Hauptbahnhof") String name,
    @Schema(description = "Adresse der Station", example = "Bahnhofplatz 1") String address,
    @Schema(description = "Anzahl verfügbarer Stellplätze", example = "24") Integer capacity,
    @Schema(description = "Betriebsstatus der Station", example = "ACTIVE") String status) {
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
