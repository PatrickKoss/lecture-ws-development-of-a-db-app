package course.library;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
  Optional<Book> findById(long id);

  List<Book> findAll();

  Book insert(Book value);
}
