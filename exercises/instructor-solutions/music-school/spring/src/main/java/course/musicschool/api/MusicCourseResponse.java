package course.musicschool.api;

import course.musicschool.domain.MusicCourse;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung eines Kursangebots")
public record MusicCourseResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutiger Kurscode", example = "MU-99") String courseCode,
    @Schema(description = "Titel des Kursangebots", example = "Songwriting") String title,
    @Schema(description = "Teilnahmegebühr in Euro", example = "80.00") BigDecimal fee) {
  public static MusicCourseResponse from(MusicCourse value) {
    return new MusicCourseResponse(value.id(), value.courseCode(), value.title(), value.fee());
  }
}
