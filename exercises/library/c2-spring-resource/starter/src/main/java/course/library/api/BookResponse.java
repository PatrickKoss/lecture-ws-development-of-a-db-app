package course.library.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.library.domain.Book;

@Schema(description = "Öffentliche Darstellung der Ressource Book")
public record BookResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String isbn,
    String title,
    Integer publicationYear,
    String subjectArea,
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
