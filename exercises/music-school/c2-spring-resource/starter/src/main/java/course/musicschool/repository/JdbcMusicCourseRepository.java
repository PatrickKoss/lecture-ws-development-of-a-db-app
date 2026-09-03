package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMusicCourseRepository implements MusicCourseRepository {
  private final JdbcTemplate jdbc;

  public JdbcMusicCourseRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<MusicCourse> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<MusicCourse> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public MusicCourse insert(MusicCourse value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByCourseCode(String courseCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
