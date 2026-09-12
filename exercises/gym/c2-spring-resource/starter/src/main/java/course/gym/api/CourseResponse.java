package course.gym.api;

import course.gym.domain.Course;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Kurse")
public record CourseResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger Kurscode",
            example = "C-999",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String courseCode,
    @Schema(
            description = "Kurstitel",
            example = "Mobility Lab",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
    @Schema(
            description = "Schwierigkeitsgrad",
            example = "BEGINNER",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String level,
    @Schema(
            description = "Dauer in Minuten",
            example = "45",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer durationMinutes,
    @Schema(
            description = "ID eines vorhandenen Raums",
            example = "1",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long roomId) {
  public static CourseResponse from(Course value) {
    return new CourseResponse(
        value.id(),
        value.courseCode(),
        value.title(),
        value.level(),
        value.durationMinutes(),
        value.roomId());
  }
}
