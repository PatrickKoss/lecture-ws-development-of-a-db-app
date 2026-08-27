package course.carworkshop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcPartRepository implements PartRepository {
  private final Database database;

  public JdbcPartRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Part> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Part> findAll() throws SQLException {
    // TODO(B3): Alle parts sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Part map(ResultSet row) throws SQLException {
    // TODO(B3): part_number, name und reorder_level den Feldern
    // partNumber, name, category, shelfCode, stockQuantity, reorderLevel, listPrice zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
