package course.library.service;

public record BookCommand(
    String isbn, String title, Integer publicationYear, String subjectArea, String shelfCode) {}
