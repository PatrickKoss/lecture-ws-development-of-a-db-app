package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMusicCourseRepository implements MusicCourseRepository {
  private static final String SELECT_FIELDS = "id, course_code, title, fee";
  private static final RowMapper<MusicCourse> ROW_MAPPER =
      (row, rowNumber) ->
          new MusicCourse(
              row.getLong("id"),
              row.getString("course_code"),
              row.getString("title"),
              row.getBigDecimal("fee"));

  private final JdbcTemplate jdbc;

  public JdbcMusicCourseRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  @Override
  public List<MusicCourse> findAll() {
    return jdbc.query(
        "SELECT " + SELECT_FIELDS + " FROM music_courses ORDER BY id", ROW_MAPPER);
  }

  @Override
  public Optional<MusicCourse> findById(long id) {
    return jdbc.query(
            "SELECT " + SELECT_FIELDS + " FROM music_courses WHERE id = ?",
            ROW_MAPPER,
            id)
        .stream()
        .findFirst();
  }

  @Override
  public MusicCourse insert(MusicCourse value) {
    String sql = "INSERT INTO music_courses (course_code, title, fee) VALUES (?, ?, ?)";
    var keyHolder = new GeneratedKeyHolder();
    jdbc.update(
        connection -> {
          var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          statement.setString(1, value.courseCode());
          statement.setString(2, value.title());
          statement.setBigDecimal(3, value.fee());
          return statement;
        },
        keyHolder);
    long id = Objects.requireNonNull(keyHolder.getKey()).longValue();
    return new MusicCourse(id, value.courseCode(), value.title(), value.fee());
  }

  @Override
  public boolean existsByCourseCode(String courseCode) {
    Integer count =
        jdbc.queryForObject(
            "SELECT COUNT(*) FROM music_courses WHERE course_code = ?",
            Integer.class,
            courseCode);
    return count != null && count > 0;
  }
}
