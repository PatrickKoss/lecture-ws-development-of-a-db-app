package course.library.service;

import course.library.api.CreateBookRequest;
import course.library.domain.Book;
import course.library.repository.BookRepository;
import course.library.web.ConflictException;
import course.library.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {
  private final BookRepository repository;

  public BookService(BookRepository repository) {
    this.repository = repository;
  }

  public List<Book> findAll() {
    return repository.findAll();
  }

  public Book findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("BOOK_NOT_FOUND", "Buch nicht gefunden"));
  }

  public Book create(CreateBookRequest request) {
    if (repository.existsByIsbn(request.isbn()))
      throw new ConflictException(
          "BOOK_ISBN_EXISTS", "Buch mit diesem Wert für isbn existiert bereits");
    return repository.insert(
        new Book(
            null,
            request.isbn(),
            request.title(),
            request.publicationYear(),
            request.subjectArea(),
            request.shelfCode()));
  }
}
