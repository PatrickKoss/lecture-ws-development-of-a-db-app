package course.cinema.api;

import course.cinema.service.MovieCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Filme")
public record CreateMovieRequest(
    @Schema(description = "Eindeutiger Filmcode", example = "F-999")
        @NotBlank
        @Size(min = 1, max = 20)
        String movieCode,
    @Schema(description = "Filmtitel", example = "Testfilm") @NotBlank @Size(min = 1, max = 200)
        String title,
    @Schema(description = "Erscheinungsjahr", example = "2026") @NotNull @Min(1888) @Max(2100)
        Integer releaseYear,
    @Schema(description = "Laufzeit in Minuten", example = "90") @NotNull @Min(1) @Max(600)
        Integer durationMinutes,
    @Schema(description = "FSK-Code", example = "FSK_6")
        @NotBlank
        @Pattern(regexp = "FSK_(0|6|12|16|18)")
        String fskCode,
    @Schema(description = "Mindestalter", example = "6") @NotNull @Min(0) @Max(18)
        Integer minimumAge) {
  public MovieCommand toCommand() {
    return new MovieCommand(movieCode, title, releaseYear, durationMinutes, fskCode, minimumAge);
  }
}
