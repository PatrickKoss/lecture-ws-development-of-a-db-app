package course.library.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Eingabe zum Anlegen einer Ressource Book")
public record CreateBookRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "9780000000000")
    @NotBlank String isbn,
    @NotBlank String title,
    @NotNull @PositiveOrZero Integer publicationYear,
    @NotBlank String subjectArea,
    @NotBlank String shelfCode) {}
