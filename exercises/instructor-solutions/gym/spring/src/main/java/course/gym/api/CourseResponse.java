package course.gym.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.gym.domain.Course;

@Schema(description = "API-Darstellung der Ressource Course")
public record CourseResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Kurscode", example = "C-199") String courseCode,
    @Schema(description = "Kurstitel", example = "Rückenfit") String title,
    @Schema(description = "Schwierigkeitsstufe", example = "BEGINNER") String level,
    @Schema(description = "Dauer in Minuten", example = "60") Integer durationMinutes,
    @Schema(description = "ID des Kursraums", example = "1") Long roomId) {
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
