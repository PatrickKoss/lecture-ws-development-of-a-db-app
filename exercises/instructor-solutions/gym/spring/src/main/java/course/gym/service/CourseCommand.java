package course.gym.service;

public record CourseCommand(
    String courseCode, String title, String level, Integer durationMinutes, Long roomId) {}
