package course.cinema.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateMovieRequest(
    @NotBlank String movieCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero Integer releaseYear,
    @NotNull @PositiveOrZero Integer durationMinutes,
    @NotBlank String fskCode,
    @NotNull @PositiveOrZero Integer minimumAge) {}
