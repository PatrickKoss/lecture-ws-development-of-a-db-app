package course.bikerental.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource Station")
public record CreateStationRequest(
    @Schema(description = "Eindeutiger Stationscode", example = "MS-NEW")
    @NotBlank String stationCode,
    @Schema(description = "Name der Station", example = "Hauptbahnhof")
    @NotBlank String name,
    @Schema(description = "Adresse der Station", example = "Bahnhofplatz 1")
    @NotBlank String address,
    @Schema(description = "Anzahl verfügbarer Stellplätze", example = "24")
    @NotNull @PositiveOrZero Integer capacity,
    @Schema(description = "Betriebsstatus der Station", example = "ACTIVE")
    @NotBlank String status
) {}
