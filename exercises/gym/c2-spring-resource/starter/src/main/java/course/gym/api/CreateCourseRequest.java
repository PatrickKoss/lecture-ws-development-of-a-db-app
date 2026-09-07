package course.gym.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource Course")
public record CreateCourseRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "C-199")
    @NotBlank String courseCode,
    @NotBlank String title,
    @NotBlank String level,
    @NotNull @PositiveOrZero Integer durationMinutes,
    @NotNull @PositiveOrZero Long roomId) {}
