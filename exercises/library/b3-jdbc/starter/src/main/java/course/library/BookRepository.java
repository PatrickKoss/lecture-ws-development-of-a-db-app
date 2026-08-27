package course.library;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
  Optional<Book> findById(long id) throws SQLException;

  List<Book> findAll() throws SQLException;
}
