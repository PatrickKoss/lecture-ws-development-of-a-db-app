package course.museum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcExhibitRepository implements ExhibitRepository {
  private static final String SELECT_COLUMNS = "id, inventory_code, title, insured_value";
  private final Database database;

  public JdbcExhibitRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Exhibit> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Exhibit> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM exhibits ORDER BY id";
    var values = new ArrayList<Exhibit>();
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
  public Exhibit insert(Exhibit value) {
    String sql = "INSERT INTO exhibits (inventory_code, title, insured_value) VALUES (?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.inventoryCode());
      statement.setString(2, value.title());
      statement.setBigDecimal(3, value.insuredValue());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted exhibits row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted exhibits row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Exhibit> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM exhibits WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Exhibit map(ResultSet row) throws SQLException {
    return new Exhibit(
        row.getLong("id"),
        row.getString("inventory_code"),
        row.getString("title"),
        row.getBigDecimal("insured_value"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The inventoryCode is already in use", exception);
    }
    return new RepositoryException("Could not access exhibits", exception);
  }
}
