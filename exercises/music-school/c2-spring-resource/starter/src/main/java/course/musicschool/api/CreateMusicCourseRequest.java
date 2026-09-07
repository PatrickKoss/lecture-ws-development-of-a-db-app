package course.musicschool.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource MusicCourse")
public record CreateMusicCourseRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "MU-99")
    @NotBlank String courseCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero @DecimalMax("500") BigDecimal fee) {}
