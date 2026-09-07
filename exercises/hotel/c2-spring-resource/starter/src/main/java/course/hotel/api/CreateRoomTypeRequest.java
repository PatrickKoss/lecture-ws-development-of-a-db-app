package course.hotel.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource RoomType")
public record CreateRoomTypeRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "NX")
    @NotBlank String typeCode,
    @NotBlank String name,
    @NotNull @PositiveOrZero Integer capacity,
    @NotNull @PositiveOrZero Integer standardPriceCents) {}
