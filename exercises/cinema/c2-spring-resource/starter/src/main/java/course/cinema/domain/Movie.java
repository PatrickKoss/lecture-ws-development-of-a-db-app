package course.cinema.domain;

public record Movie(
    Long id,
    String movieCode,
    String title,
    Integer releaseYear,
    Integer durationMinutes,
    String fskCode,
    Integer minimumAge) {}
