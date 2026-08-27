package course.cinema.api;

import course.cinema.domain.Movie;

public record MovieResponse(
    Long id,
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
