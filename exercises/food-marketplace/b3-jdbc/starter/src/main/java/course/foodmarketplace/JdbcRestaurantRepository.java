package course.foodmarketplace;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcRestaurantRepository implements RestaurantRepository {
  private final Database database;

  public JdbcRestaurantRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Restaurant> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Restaurant> findAll() throws SQLException {
    // TODO(B3): Alle restaurants sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Restaurant map(ResultSet row) throws SQLException {
    // TODO(B3): partner_number, name und commission_rate den Feldern
    // partnerNumber, name, street, postalCode, city, commissionRate, active zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
