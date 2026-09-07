package course.musicschool.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.musicschool.domain.MusicCourse;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource MusicCourse")
public record MusicCourseResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String courseCode, String title, BigDecimal fee) {
  public static MusicCourseResponse from(MusicCourse value) {
    return new MusicCourseResponse(value.id(), value.courseCode(), value.title(), value.fee());
  }
}
