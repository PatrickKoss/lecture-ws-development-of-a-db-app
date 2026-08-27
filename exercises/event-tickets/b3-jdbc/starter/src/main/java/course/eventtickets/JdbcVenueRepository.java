package course.eventtickets;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcVenueRepository implements VenueRepository {
  private final Database database;

  public JdbcVenueRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Venue> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Venue> findAll() throws SQLException {
    // TODO(B3): Alle venues sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Venue map(ResultSet row) throws SQLException {
    // TODO(B3): venue_code, name und capacity den Feldern
    // venueCode, name, street, postalCode, city, capacity zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
