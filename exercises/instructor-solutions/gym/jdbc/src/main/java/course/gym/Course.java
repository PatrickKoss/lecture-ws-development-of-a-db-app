package course.gym;

public record Course(
    Long id, String courseCode, String title, String level, Integer durationMinutes, Long roomId) {}
