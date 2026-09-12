package course.musicschool.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen eines Kursangebots")
public record CreateMusicCourseRequest(
    @Schema(description = "Eindeutiger Kurscode", example = "MU-99")
        @NotBlank
        String courseCode,
    @Schema(description = "Titel des Kursangebots", example = "Songwriting")
        @NotBlank
        String title,
    @Schema(description = "Teilnahmegebühr in Euro", example = "80.00")
        @NotNull
        @PositiveOrZero
        @DecimalMax("500")
        BigDecimal fee) {}
