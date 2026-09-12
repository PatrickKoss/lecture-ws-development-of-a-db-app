package course.library.api;

import course.library.domain.Book;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Bücher")
public record BookResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "ISBN mit 13 Ziffern",
            example = "9780000000000",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String isbn,
    @Schema(
            description = "Buchtitel",
            example = "Testbuch",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
    @Schema(
            description = "Erscheinungsjahr",
            example = "2026",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Integer publicationYear,
    @Schema(
            description = "Sachgebiet",
            example = "Informatik",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String subjectArea,
    @Schema(
            description = "Signatur des Regals",
            example = "I-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String shelfCode) {
  public static BookResponse from(Book value) {
    return new BookResponse(
        value.id(),
        value.isbn(),
        value.title(),
        value.publicationYear(),
        value.subjectArea(),
        value.shelfCode());
  }
}
