package course.cinema.api;

import course.cinema.domain.Movie;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Filme")
public record MovieResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Filmcode",
            example = "F-999",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String movieCode,
    @Schema(
            description = "Filmtitel",
            example = "Testfilm",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
    @Schema(
            description = "Erscheinungsjahr",
            example = "2026",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer releaseYear,
    @Schema(
            description = "Laufzeit in Minuten",
            example = "90",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer durationMinutes,
    @Schema(
            description = "FSK-Code",
            example = "FSK_6",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String fskCode,
    @Schema(
            description = "Mindestalter",
            example = "6",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer minimumAge) {
  public static MovieResponse from(Movie value) {
    return new MovieResponse(
        value.id(),
        value.movieCode(),
        value.title(),
        value.releaseYear(),
        value.durationMinutes(),
        value.fskCode(),
        value.minimumAge());
  }
}
