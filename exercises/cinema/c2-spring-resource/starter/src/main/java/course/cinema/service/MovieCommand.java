package course.cinema.service;

public record MovieCommand(
    String movieCode,
    String title,
    Integer releaseYear,
    Integer durationMinutes,
    String fskCode,
    Integer minimumAge) {}
