package course.carworkshop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcPartRepository implements PartRepository {
  private static final String SELECT_COLUMNS =
      "id, part_number, name, category, shelf_code, stock_quantity, reorder_level, list_price";
  private final Database database;

  public JdbcPartRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Part> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Part> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM parts ORDER BY id";
    var values = new ArrayList<Part>();
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
  public Part insert(Part value) {
    String sql =
        "INSERT INTO parts (part_number, name, category, shelf_code, stock_quantity, reorder_level,"
            + " list_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.partNumber());
      statement.setString(2, value.name());
      statement.setString(3, value.category());
      statement.setString(4, value.shelfCode());
      statement.setInt(5, value.stockQuantity());
      statement.setInt(6, value.reorderLevel());
      statement.setBigDecimal(7, value.listPrice());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted parts row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted parts row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Part> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM parts WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Part map(ResultSet row) throws SQLException {
    return new Part(
        row.getLong("id"),
        row.getString("part_number"),
        row.getString("name"),
        row.getString("category"),
        row.getString("shelf_code"),
        row.getInt("stock_quantity"),
        row.getInt("reorder_level"),
        row.getBigDecimal("list_price"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The partNumber is already in use", exception);
    }
    return new RepositoryException("Could not access parts", exception);
  }
}
