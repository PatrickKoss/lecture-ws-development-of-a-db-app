package course.hotel.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource RoomType")
public record CreateRoomTypeRequest(
    @Schema(description = "Eindeutiger Typcode", example = "NX")
    @NotBlank String typeCode,
    @Schema(description = "Bezeichnung des Zimmertyps", example = "Next Deluxe")
    @NotBlank String name,
    @Schema(description = "Maximale Personenzahl", example = "2")
    @NotNull @PositiveOrZero Integer capacity,
    @Schema(description = "Standardpreis in Cent", example = "14900")
    @NotNull @PositiveOrZero Integer standardPriceCents
) {}
