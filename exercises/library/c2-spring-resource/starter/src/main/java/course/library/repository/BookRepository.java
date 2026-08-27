package course.library.repository;

import course.library.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
  List<Book> findAll();

  Optional<Book> findById(long id);

  Book insert(Book value);

  boolean existsByIsbn(String isbn);
}
