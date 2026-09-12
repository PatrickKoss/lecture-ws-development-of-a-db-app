package course.hotel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcRoomTypeRepository implements RoomTypeRepository {
  private static final String SELECT_COLUMNS =
      "id, type_code, name, capacity, standard_price_cents";
  private final Database database;

  public JdbcRoomTypeRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<RoomType> findById(long id) throws SQLException {
    try (var connection = database.open()) {
      return findById(connection, id);
    }
  }

  @Override
  public List<RoomType> findAll() throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM room_types ORDER BY id";
    var values = new ArrayList<RoomType>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) values.add(map(rows));
    }
    return List.copyOf(values);
  }

  @Override
  public RoomType insert(RoomType value) throws SQLException {
    String sql =
        "INSERT INTO room_types (type_code, name, capacity, standard_price_cents) VALUES (?, ?, ?,"
            + " ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.typeCode());
      statement.setString(2, value.name());
      statement.setInt(3, value.capacity());
      statement.setInt(4, value.standardPriceCents());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted room_types row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted room_types row could not be read"));
    }
  }

  private Optional<RoomType> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM room_types WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private RoomType map(ResultSet row) throws SQLException {
    return new RoomType(
        row.getLong("id"),
        row.getString("type_code"),
        row.getString("name"),
        row.getInt("capacity"),
        row.getInt("standard_price_cents"));
  }
}
