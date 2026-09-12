package course.gym.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource Course")
public record CreateCourseRequest(
    @Schema(description = "Eindeutiger Kurscode", example = "C-199")
    @NotBlank String courseCode,
    @Schema(description = "Kurstitel", example = "Rückenfit")
    @NotBlank String title,
    @Schema(description = "Schwierigkeitsstufe", example = "BEGINNER")
    @NotBlank String level,
    @Schema(description = "Dauer in Minuten", example = "60")
    @NotNull @PositiveOrZero Integer durationMinutes,
    @Schema(description = "ID des Kursraums", example = "1")
    @NotNull @PositiveOrZero Long roomId
) {}
