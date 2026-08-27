package course.gym.api;

import course.gym.domain.Course;

public record CourseResponse(
    Long id, String courseCode, String title, String level, Integer durationMinutes, Long roomId) {
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
