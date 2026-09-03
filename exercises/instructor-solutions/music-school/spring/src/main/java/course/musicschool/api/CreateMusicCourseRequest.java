package course.musicschool.api;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public record CreateMusicCourseRequest(
    @NotBlank String courseCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero @DecimalMax("500") BigDecimal fee) {}
