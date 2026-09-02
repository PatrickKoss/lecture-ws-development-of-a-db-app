package course.museum;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcExhibitRepository implements ExhibitRepository {
  private final Database database;

  public JdbcExhibitRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Exhibit> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Exhibit> findAll() throws SQLException {
    // TODO(B3): Alle exhibits sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Exhibit map(ResultSet row) throws SQLException {
    // TODO(B3): inventory_code, title und insured_value den Feldern
    // inventoryCode, title, insuredValue zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
