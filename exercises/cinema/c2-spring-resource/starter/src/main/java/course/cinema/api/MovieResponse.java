package course.cinema.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.cinema.domain.Movie;

@Schema(description = "Öffentliche Darstellung der Ressource Movie")
public record MovieResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String movieCode,
    String title,
    Integer releaseYear,
    Integer durationMinutes,
    String fskCode,
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
