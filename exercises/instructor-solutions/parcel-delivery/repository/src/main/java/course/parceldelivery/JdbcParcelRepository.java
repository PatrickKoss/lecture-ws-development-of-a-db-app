package course.parceldelivery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcParcelRepository implements ParcelRepository {
  private static final String SELECT_COLUMNS = "id, tracking_code, recipient, weight";
  private final Database database;

  public JdbcParcelRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Parcel> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Parcel> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM parcels ORDER BY id";
    var values = new ArrayList<Parcel>();
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
  public Parcel insert(Parcel value) {
    String sql = "INSERT INTO parcels (tracking_code, recipient, weight) VALUES (?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.trackingCode());
      statement.setString(2, value.recipient());
      statement.setBigDecimal(3, value.weight());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted parcels row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted parcels row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Parcel> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM parcels WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Parcel map(ResultSet row) throws SQLException {
    return new Parcel(
        row.getLong("id"),
        row.getString("tracking_code"),
        row.getString("recipient"),
        row.getBigDecimal("weight"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The trackingCode is already in use", exception);
    }
    return new RepositoryException("Could not access parcels", exception);
  }
}
