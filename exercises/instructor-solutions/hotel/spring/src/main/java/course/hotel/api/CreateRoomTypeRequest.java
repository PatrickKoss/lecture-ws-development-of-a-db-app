package course.hotel.api;

import course.hotel.service.RoomTypeCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Zimmertypen")
public record CreateRoomTypeRequest(
    @Schema(description = "Eindeutiger Typcode", example = "NX") @NotBlank @Size(min = 1, max = 10)
        String typeCode,
    @Schema(description = "Name des Zimmertyps", example = "Testzimmer")
        @NotBlank
        @Size(min = 1, max = 80)
        String name,
    @Schema(description = "Maximale Belegung", example = "2") @NotNull @Min(1) @Max(6)
        Integer capacity,
    @Schema(description = "Standardpreis in Cent", example = "14900") @NotNull @Positive
        Integer standardPriceCents) {
  public RoomTypeCommand toCommand() {
    return new RoomTypeCommand(typeCode, name, capacity, standardPriceCents);
  }
}
