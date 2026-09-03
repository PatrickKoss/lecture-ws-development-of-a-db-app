package course.musicschool;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcMusicCourseRepository implements MusicCourseRepository {
  private final Database database;

  public JdbcMusicCourseRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<MusicCourse> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<MusicCourse> findAll() throws SQLException {
    // TODO(B3): Alle courses sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private MusicCourse map(ResultSet row) throws SQLException {
    // TODO(B3): course_code, title und fee den Feldern
    // courseCode, title, fee zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
