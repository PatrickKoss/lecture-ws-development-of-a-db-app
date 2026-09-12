package course.musicschool.api;

import course.musicschool.service.MusicCourseCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Kursangebote")
public record CreateMusicCourseRequest(
    @Schema(description = "Eindeutiger Kurscode", example = "MU-99")
        @NotBlank
        @Size(min = 1, max = 40)
        String courseCode,
    @Schema(description = "Titel des Kursangebots", example = "Songwriting")
        @NotBlank
        @Size(min = 1, max = 160)
        String title,
    @Schema(description = "Kursgebühr", example = "80.00")
        @NotNull
        @PositiveOrZero
        @DecimalMax("500")
        BigDecimal fee) {
  public MusicCourseCommand toCommand() {
    return new MusicCourseCommand(courseCode, title, fee);
  }
}
