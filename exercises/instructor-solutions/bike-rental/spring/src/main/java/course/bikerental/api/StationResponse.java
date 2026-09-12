package course.bikerental.api;

import course.bikerental.domain.Station;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Stationen")
public record StationResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Stationscode",
            example = "N99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String stationCode,
    @Schema(
            description = "Name der Station",
            example = "Neue Station",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Postanschrift",
            example = "Neue Straße 1, Münster",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String address,
    @Schema(
            description = "Anzahl der Stellplätze",
            example = "12",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer capacity,
    @Schema(
            description = "Betriebsstatus",
            example = "PLANNED",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String status) {
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
