package course.library.domain;

public record Book(
    Long id,
    String isbn,
    String title,
    Integer publicationYear,
    String subjectArea,
    String shelfCode) {}
