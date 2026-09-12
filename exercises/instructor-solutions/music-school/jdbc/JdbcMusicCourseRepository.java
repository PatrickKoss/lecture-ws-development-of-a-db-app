package course.musicschool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class JdbcMusicCourseRepository implements MusicCourseRepository {
  private final Database database;

  public JdbcMusicCourseRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<MusicCourse> findById(long id) throws SQLException {
    String sql = "SELECT id, course_code, title, fee FROM music_courses WHERE id = ?";
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql)) {
      statement.setLong(1, id);
      try (var rows = statement.executeQuery()) {
        if (!rows.next()) return Optional.empty();
        return Optional.of(map(rows));
      }
    }
  }

  @Override
  public List<MusicCourse> findAll() throws SQLException {
    String sql = "SELECT id, course_code, title, fee FROM music_courses ORDER BY id";
    var courses = new ArrayList<MusicCourse>();
    try (var connection = database.open();
        var statement = connection.prepareStatement(sql);
        var rows = statement.executeQuery()) {
      while (rows.next()) courses.add(map(rows));
    }
    return courses;
  }

  private MusicCourse map(ResultSet row) throws SQLException {
    return new MusicCourse(
        row.getLong("id"),
        row.getString("course_code"),
        row.getString("title"),
        row.getBigDecimal("fee"));
  }
}
