package course.library.repository;

import course.library.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcBookRepository implements BookRepository {
  private final JdbcTemplate jdbc;

  public JdbcBookRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Book> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Book> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Book insert(Book value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByIsbn(String isbn) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
