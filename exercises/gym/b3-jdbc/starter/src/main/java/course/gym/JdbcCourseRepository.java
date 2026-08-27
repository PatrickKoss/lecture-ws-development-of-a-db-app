package course.gym;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcCourseRepository implements CourseRepository {
  private final Database database;

  public JdbcCourseRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Course> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Course> findAll() throws SQLException {
    // TODO(B3): Alle courses sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Course map(ResultSet row) throws SQLException {
    // TODO(B3): course_code, title und duration_minutes den Feldern
    // courseCode, title, level, durationMinutes, roomId zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
