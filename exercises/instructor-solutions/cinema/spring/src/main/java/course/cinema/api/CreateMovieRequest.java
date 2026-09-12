package course.cinema.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource Movie")
public record CreateMovieRequest(
    @Schema(description = "Eindeutiger Filmcode", example = "F-199")
    @NotBlank String movieCode,
    @Schema(description = "Filmtitel", example = "Die letzte Vorstellung")
    @NotBlank String title,
    @Schema(description = "Erscheinungsjahr", example = "2026")
    @NotNull @PositiveOrZero Integer releaseYear,
    @Schema(description = "Laufzeit in Minuten", example = "118")
    @NotNull @PositiveOrZero Integer durationMinutes,
    @Schema(description = "Code der Altersfreigabe", example = "FSK12")
    @NotBlank String fskCode,
    @Schema(description = "Mindestalter", example = "12")
    @NotNull @PositiveOrZero Integer minimumAge
) {}
