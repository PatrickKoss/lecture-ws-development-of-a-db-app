package course.library.service;

import course.library.domain.Book;
import course.library.repository.BookRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
        .orElseThrow(() -> new ResourceNotFoundException("BOOK_NOT_FOUND", "Buch nicht gefunden"));
  }

  @Transactional
  public Book create(BookCommand command) {
    if (repository.existsByIsbn(command.isbn())) {
      throw duplicate();
    }
    return repository.save(
        new Book(
            null,
            command.isbn(),
            command.title(),
            command.publicationYear(),
            command.subjectArea(),
            command.shelfCode()));
  }

  @Transactional
  public Book replace(long id, BookCommand command) {
    findById(id);
    if (repository.existsByIsbnAndIdNot(command.isbn(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Book(
            id,
            command.isbn(),
            command.title(),
            command.publicationYear(),
            command.subjectArea(),
            command.shelfCode()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("ISBN_EXISTS", "isbn ist bereits vergeben");
  }
}
