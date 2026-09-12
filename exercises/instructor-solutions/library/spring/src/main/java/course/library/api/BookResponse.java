package course.library.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.library.domain.Book;

@Schema(description = "API-Darstellung der Ressource Book")
public record BookResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige ISBN", example = "9780000000000") String isbn,
    @Schema(description = "Buchtitel", example = "Datenbanken in der Praxis") String title,
    @Schema(description = "Erscheinungsjahr", example = "2026") Integer publicationYear,
    @Schema(description = "Sachgebiet", example = "Informatik") String subjectArea,
    @Schema(description = "Regalplatz", example = "INF-12") String shelfCode) {
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
