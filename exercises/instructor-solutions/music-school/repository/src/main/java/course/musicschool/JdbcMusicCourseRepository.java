package course.musicschool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcMusicCourseRepository implements MusicCourseRepository {
  private static final String SELECT_COLUMNS = "id, course_code, title, fee";
  private final Database database;

  public JdbcMusicCourseRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<MusicCourse> findById(long id) {
    try (var connection = database.open()) {
      return findById(connection, id);
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  @Override
  public List<MusicCourse> findAll() {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM music_courses ORDER BY id";
    var values = new ArrayList<MusicCourse>();
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
  public MusicCourse insert(MusicCourse value) {
    String sql = "INSERT INTO music_courses (course_code, title, fee) VALUES (?, ?, ?)";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, value.courseCode());
      statement.setString(2, value.title());
      statement.setBigDecimal(3, value.fee());
      if (statement.executeUpdate() != 1) {
        throw new SQLException("Expected one inserted music_courses row");
      }

      long generatedId;
      try (var keys = statement.getGeneratedKeys()) {
        if (!keys.next()) throw new SQLException("Database returned no generated ID");
        generatedId = keys.getLong(1);
      }
      return findById(connection, generatedId)
          .orElseThrow(() -> new SQLException("Inserted music_courses row could not be read"));
    } catch (SQLException exception) {
      throw translate(exception);
    }
  }

  private Optional<MusicCourse> findById(Connection connection, long id) throws SQLException {
    String sql = "SELECT " + SELECT_COLUMNS + " FROM music_courses WHERE id = ?";
    try (var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        return rows.next() ? Optional.of(map(rows)) : Optional.empty();
      }
    }
  }

  private MusicCourse map(ResultSet row) throws SQLException {
    return new MusicCourse(
        row.getLong("id"),
        row.getString("course_code"),
        row.getString("title"),
        row.getBigDecimal("fee"));
  }

  private RepositoryException translate(SQLException exception) {
    String message = exception.getMessage();
    if (message != null && message.contains("UNIQUE constraint failed")) {
      return new DuplicateKeyException("The courseCode is already in use", exception);
    }
    return new RepositoryException("Could not access music_courses", exception);
  }
}
