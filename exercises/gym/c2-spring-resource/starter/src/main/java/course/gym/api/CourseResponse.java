package course.gym.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.gym.domain.Course;

@Schema(description = "Öffentliche Darstellung der Ressource Course")
public record CourseResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String courseCode, String title, String level, Integer durationMinutes, Long roomId) {
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
