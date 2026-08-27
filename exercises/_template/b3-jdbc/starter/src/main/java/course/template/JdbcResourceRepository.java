package course.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcResourceRepository implements ResourceRepository {
  private final Database database;

  public JdbcResourceRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Resource> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Resource> findAll() throws SQLException {
    // TODO(B3): Alle resources sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Resource map(ResultSet row) throws SQLException {
    // TODO(B3): resource_code, name und measure den Feldern
    // resourceCode, name, measure zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
