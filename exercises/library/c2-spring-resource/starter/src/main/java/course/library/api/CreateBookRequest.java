package course.library.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateBookRequest(
    @NotBlank String isbn,
    @NotBlank String title,
    @NotNull @PositiveOrZero Integer publicationYear,
    @NotBlank String subjectArea,
    @NotBlank String shelfCode) {}
