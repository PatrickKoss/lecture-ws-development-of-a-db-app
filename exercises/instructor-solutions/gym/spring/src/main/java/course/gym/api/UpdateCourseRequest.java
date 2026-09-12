package course.gym.api;

import course.gym.service.CourseCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Kurse")
public record UpdateCourseRequest(
    @Schema(description = "Eindeutiger Kurscode", example = "C-999")
        @NotBlank
        @Size(min = 2, max = 20)
        String courseCode,
    @Schema(description = "Kurstitel", example = "Mobility Lab") @NotBlank @Size(min = 1, max = 100)
        String title,
    @Schema(description = "Schwierigkeitsgrad", example = "BEGINNER")
        @NotBlank
        @Pattern(regexp = "BEGINNER|INTERMEDIATE|ADVANCED")
        String level,
    @Schema(description = "Dauer in Minuten", example = "45") @NotNull @Positive
        Integer durationMinutes,
    @Schema(description = "ID eines vorhandenen Raums", example = "1") @NotNull @Positive
        Long roomId) {
  public CourseCommand toCommand() {
    return new CourseCommand(courseCode, title, level, durationMinutes, roomId);
  }
}
