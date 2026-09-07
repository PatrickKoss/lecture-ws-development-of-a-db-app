package course.bikerental.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource Station")
public record CreateStationRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "MS-NEW")
    @NotBlank String stationCode,
    @NotBlank String name,
    @NotBlank String address,
    @NotNull @PositiveOrZero Integer capacity,
    @NotBlank String status) {}
