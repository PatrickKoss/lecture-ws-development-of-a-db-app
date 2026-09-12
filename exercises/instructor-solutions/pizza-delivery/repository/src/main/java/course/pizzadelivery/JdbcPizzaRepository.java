package course.pizzadelivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcPizzaRepository implements PizzaRepository {
  private static final String SELECT_COLUMNS =
      "id, pizza_number, name, category, oven_station, base_price, active";
  private final Database database;

  public JdbcPizzaRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Pizza> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Pizza> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM pizzas ORDER BY id";
    var values = new ArrayList<Pizza>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
      return List.copyOf(values);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public Pizza insert(Pizza value) {
    String sql =
        "INSERT INTO pizzas (pizza_number, name, category, oven_station, base_price, active) VALUES"
            + " (?, ?, ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.pizzaNumber());
      statement.setString(2, value.name());
      statement.setString(3, value.category());
      statement.setString(4, value.ovenStation());
      statement.setBigDecimal(5, value.basePrice());
      statement.setBoolean(6, value.active());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted pizzas row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted pizzas row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Pizza> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM pizzas WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Pizza map(ResultSet row) throws SQLException {
    return new Pizza(
        row.getLong("id"),
        row.getString("pizza_number"),
        row.getString("name"),
        row.getString("category"),
        row.getString("oven_station"),
        row.getBigDecimal("base_price"),
        row.getBoolean("active"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The pizzaNumber is already in use", exception);
    }
    return new RepositoryException("Could not access pizzas", exception);
  }
}
