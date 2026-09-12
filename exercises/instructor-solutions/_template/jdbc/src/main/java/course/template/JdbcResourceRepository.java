package course.template;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcResourceRepository implements ResourceRepository {
  private static final String SELECT_COLUMNS = "id, resource_code, name, measure";
  private final Database database;

  public JdbcResourceRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Resource> findById(long id) throws SQLException {
    try (var connection = database.open()) {
      return findById(connection, id);
    }
  }

  @Override
  public List<Resource> findAll() throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM resources ORDER BY id";
    var values = new ArrayList<Resource>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
    }
    return List.copyOf(values);
  }

  @Override
  public Resource insert(Resource value) throws SQLException {
    String sql = "INSERT INTO resources (resource_code, name, measure) VALUES (?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.resourceCode());
      statement.setString(2, value.name());
      statement.setBigDecimal(3, value.measure());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted resources row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted resources row could not be read"));
    }
  }

  private Optional<Resource> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM resources WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Resource map(ResultSet row) throws SQLException {
    return new Resource(
        row.getLong("id"),
        row.getString("resource_code"),
        row.getString("name"),
        row.getBigDecimal("measure"));
  }
}
