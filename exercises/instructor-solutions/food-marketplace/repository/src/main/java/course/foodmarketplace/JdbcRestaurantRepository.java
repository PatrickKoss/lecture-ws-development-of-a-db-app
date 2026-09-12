package course.foodmarketplace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcRestaurantRepository implements RestaurantRepository {
  private static final String SELECT_COLUMNS =
      "id, partner_number, name, street, postal_code, city, commission_rate, active";
  private final Database database;

  public JdbcRestaurantRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Restaurant> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Restaurant> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM restaurants ORDER BY id";
    var values = new ArrayList<Restaurant>();
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
  public Restaurant insert(Restaurant value) {
    String sql =
        "INSERT INTO restaurants (partner_number, name, street, postal_code, city, commission_rate,"
            + " active) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.partnerNumber());
      statement.setString(2, value.name());
      statement.setString(3, value.street());
      statement.setString(4, value.postalCode());
      statement.setString(5, value.city());
      statement.setBigDecimal(6, value.commissionRate());
      statement.setBoolean(7, value.active());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted restaurants row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted restaurants row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Restaurant> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM restaurants WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Restaurant map(ResultSet row) throws SQLException {
    return new Restaurant(
        row.getLong("id"),
        row.getString("partner_number"),
        row.getString("name"),
        row.getString("street"),
        row.getString("postal_code"),
        row.getString("city"),
        row.getBigDecimal("commission_rate"),
        row.getBoolean("active"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The partnerNumber is already in use", exception);
    }
    return new RepositoryException("Could not access restaurants", exception);
  }
}
