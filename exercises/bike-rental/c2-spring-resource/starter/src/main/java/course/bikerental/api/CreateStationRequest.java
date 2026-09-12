package course.bikerental.api;

import course.bikerental.service.StationCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Stationen")
public record CreateStationRequest(
    @Schema(description = "Eindeutiger Stationscode", example = "N99")
        @NotBlank
        @Size(min = 3, max = 8)
        String stationCode,
    @Schema(description = "Name der Station", example = "Neue Station")
        @NotBlank
        @Size(min = 1, max = 80)
        String name,
    @Schema(description = "Postanschrift", example = "Neue Straße 1, Münster")
        @NotBlank
        @Size(min = 1, max = 120)
        String address,
    @Schema(description = "Anzahl der Stellplätze", example = "12") @NotNull @Min(5) @Max(100)
        Integer capacity,
    @Schema(description = "Betriebsstatus", example = "PLANNED")
        @NotBlank
        @Pattern(regexp = "ACTIVE|PLANNED|CLOSED")
        String status) {
  public StationCommand toCommand() {
    return new StationCommand(stationCode, name, address, capacity, status);
  }
}
