package course.library.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabedaten für die Ressource Book")
public record CreateBookRequest(
    @Schema(description = "Eindeutige ISBN", example = "9780000000000")
    @NotBlank String isbn,
    @Schema(description = "Buchtitel", example = "Datenbanken in der Praxis")
    @NotBlank String title,
    @Schema(description = "Erscheinungsjahr", example = "2026")
    @NotNull @PositiveOrZero Integer publicationYear,
    @Schema(description = "Sachgebiet", example = "Informatik")
    @NotBlank String subjectArea,
    @Schema(description = "Regalplatz", example = "INF-12")
    @NotBlank String shelfCode
) {}
