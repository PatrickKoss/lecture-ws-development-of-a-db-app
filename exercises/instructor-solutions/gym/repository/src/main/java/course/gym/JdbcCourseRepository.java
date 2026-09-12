package course.gym;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcCourseRepository implements CourseRepository {
  private static final String SELECT_COLUMNS =
      "id, course_code, title, level, duration_minutes, room_id";
  private final Database database;

  public JdbcCourseRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Course> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<Course> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM courses ORDER BY id";
    var values = new ArrayList<Course>();
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
  public Course insert(Course value) {
    String sql =
        "INSERT INTO courses (course_code, title, level, duration_minutes, room_id) VALUES (?, ?,"
            + " ?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.courseCode());
      statement.setString(2, value.title());
      statement.setString(3, value.level());
      statement.setInt(4, value.durationMinutes());
      if (value.roomId() == null) statement.setNull(5, Types.INTEGER);
      else statement.setLong(5, value.roomId());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted courses row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted courses row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<Course> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM courses WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private Course map(ResultSet row) throws SQLException {
    return new Course(
        row.getLong("id"),
        row.getString("course_code"),
        row.getString("title"),
        row.getString("level"),
        row.getInt("duration_minutes"),
        nullableLong(row, "room_id"));
  }

  private Long nullableLong(ResultSet row, String column) throws SQLException {
    long value = row.getLong(column);
    return row.wasNull() ? null : value;
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The courseCode is already in use", exception);
    }
    return new RepositoryException("Could not access courses", exception);
  }
}
