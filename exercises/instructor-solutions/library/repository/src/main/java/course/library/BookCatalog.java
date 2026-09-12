package course.library;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class BookCatalog {
  private final BookRepository repository;

  public BookCatalog(BookRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Book> list() {
    return repository.findAll();
  }

  public Optional<Book> find(long id) {
    return repository.findById(id);
  }

  public Book add(Book value) {
    return repository.insert(value);
  }
}
