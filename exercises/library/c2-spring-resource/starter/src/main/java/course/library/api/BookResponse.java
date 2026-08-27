package course.library.api;

import course.library.domain.Book;

public record BookResponse(
    Long id,
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
