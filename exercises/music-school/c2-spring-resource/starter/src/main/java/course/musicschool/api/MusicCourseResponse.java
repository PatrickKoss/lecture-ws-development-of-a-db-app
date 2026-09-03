package course.musicschool.api;

import course.musicschool.domain.MusicCourse;
import java.math.BigDecimal;

public record MusicCourseResponse(Long id, String courseCode, String title, BigDecimal fee) {
  public static MusicCourseResponse from(MusicCourse value) {
    return new MusicCourseResponse(value.id(), value.courseCode(), value.title(), value.fee());
  }
}
