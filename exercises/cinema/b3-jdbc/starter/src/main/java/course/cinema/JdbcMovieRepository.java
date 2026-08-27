package course.cinema;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcMovieRepository implements MovieRepository {
  private final Database database;

  public JdbcMovieRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Movie> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Movie> findAll() throws SQLException {
    // TODO(B3): Alle movies sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Movie map(ResultSet row) throws SQLException {
    // TODO(B3): movie_code, title und duration_minutes den Feldern
    // movieCode, title, releaseYear, durationMinutes, fskCode, minimumAge zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
