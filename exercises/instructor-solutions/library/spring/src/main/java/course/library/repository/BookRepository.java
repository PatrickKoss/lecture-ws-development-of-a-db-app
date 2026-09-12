package course.library.repository;

import course.library.domain.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
  List<Book> findAll();

  Optional<Book> findById(long id);

  Book save(Book value);

  boolean existsByIsbn(String value);

  boolean existsByIsbnAndIdNot(String value, long id);

  void deleteById(long id);
}
