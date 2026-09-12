package course.bikerental;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcStationRepository implements StationRepository {
  private static final String SELECT_COLUMNS = "id, station_code, name, address, capacity, status";
  private final Database database;

  public JdbcStationRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Station> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Station> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM stations ORDER BY id";
    var values = new ArrayList<Station>();
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
  public Station insert(Station value) {
    String sql =
        "INSERT INTO stations (station_code, name, address, capacity, status) VALUES (?, ?, ?, ?,"
            + " ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.stationCode());
      statement.setString(2, value.name());
      statement.setString(3, value.address());
      statement.setInt(4, value.capacity());
      statement.setString(5, value.status());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted stations row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted stations row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Station> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM stations WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Station map(ResultSet row) throws SQLException {
    return new Station(
        row.getLong("id"),
        row.getString("station_code"),
        row.getString("name"),
        row.getString("address"),
        row.getInt("capacity"),
        row.getString("status"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The stationCode is already in use", exception);
    }
    return new RepositoryException("Could not access stations", exception);
  }
}
