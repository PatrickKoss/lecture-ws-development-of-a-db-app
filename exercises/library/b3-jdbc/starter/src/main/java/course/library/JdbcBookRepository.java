package course.library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcBookRepository implements BookRepository {
  private final Database database;

  public JdbcBookRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Book> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Book> findAll() throws SQLException {
    // TODO(B3): Alle books sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Book map(ResultSet row) throws SQLException {
    // TODO(B3): isbn, title und publication_year den Feldern
    // isbn, title, publicationYear, subjectArea, shelfCode zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
