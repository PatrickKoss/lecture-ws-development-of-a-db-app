package course.cinema.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource Movie")
public record CreateMovieRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "F-199")
    @NotBlank String movieCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero Integer releaseYear,
    @NotNull @PositiveOrZero Integer durationMinutes,
    @NotBlank String fskCode,
    @NotNull @PositiveOrZero Integer minimumAge) {}
