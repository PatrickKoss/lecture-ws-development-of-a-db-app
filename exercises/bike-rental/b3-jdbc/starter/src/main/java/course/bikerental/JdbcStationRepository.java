package course.bikerental;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcStationRepository implements StationRepository {
  private final Database database;

  public JdbcStationRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Station> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Station> findAll() throws SQLException {
    // TODO(B3): Alle stations sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Station map(ResultSet row) throws SQLException {
    // TODO(B3): station_code, name und capacity den Feldern
    // stationCode, name, address, capacity, status zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
