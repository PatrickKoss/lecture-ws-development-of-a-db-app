package course.pizzadelivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcPizzaRepository implements PizzaRepository {
  private final Database database;

  public JdbcPizzaRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Pizza> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Pizza> findAll() throws SQLException {
    // TODO(B3): Alle pizzas sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Pizza map(ResultSet row) throws SQLException {
    // TODO(B3): pizza_number, name und base_price den Feldern
    // pizzaNumber, name, category, ovenStation, basePrice, active zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
