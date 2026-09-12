package course.eventtickets;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcVenueRepository implements VenueRepository {
  private static final String SELECT_COLUMNS =
      "id, venue_code, name, street, postal_code, city, capacity";
  private final Database database;

  public JdbcVenueRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Venue> findById(long id) throws SQLException {
    try (var connection = database.open()) {
      return findById(connection, id);
    }
  }

  @Override
  public List<Venue> findAll() throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM venues ORDER BY id";
    var values = new ArrayList<Venue>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
    }
    return List.copyOf(values);
  }

  @Override
  public Venue insert(Venue value) throws SQLException {
    String sql =
        "INSERT INTO venues (venue_code, name, street, postal_code, city, capacity) VALUES (?, ?,"
            + " ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.venueCode());
      statement.setString(2, value.name());
      statement.setString(3, value.street());
      statement.setString(4, value.postalCode());
      statement.setString(5, value.city());
      statement.setInt(6, value.capacity());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted venues row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted venues row could not be read"));
    }
  }

  private Optional<Venue> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM venues WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Venue map(ResultSet row) throws SQLException {
    return new Venue(
        row.getLong("id"),
        row.getString("venue_code"),
        row.getString("name"),
        row.getString("street"),
        row.getString("postal_code"),
        row.getString("city"),
        row.getInt("capacity"));
  }
}
