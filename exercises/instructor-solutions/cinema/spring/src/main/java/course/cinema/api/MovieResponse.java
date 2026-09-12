package course.cinema.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.cinema.domain.Movie;

@Schema(description = "API-Darstellung der Ressource Movie")
public record MovieResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Filmcode", example = "F-199") String movieCode,
    @Schema(description = "Filmtitel", example = "Die letzte Vorstellung") String title,
    @Schema(description = "Erscheinungsjahr", example = "2026") Integer releaseYear,
    @Schema(description = "Laufzeit in Minuten", example = "118") Integer durationMinutes,
    @Schema(description = "Code der Altersfreigabe", example = "FSK12") String fskCode,
    @Schema(description = "Mindestalter", example = "12") Integer minimumAge) {
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
