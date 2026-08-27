package course.gym.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateCourseRequest(
    @NotBlank String courseCode,
    @NotBlank String title,
    @NotBlank String level,
    @NotNull @PositiveOrZero Integer durationMinutes,
    @NotNull @PositiveOrZero Long roomId) {}
