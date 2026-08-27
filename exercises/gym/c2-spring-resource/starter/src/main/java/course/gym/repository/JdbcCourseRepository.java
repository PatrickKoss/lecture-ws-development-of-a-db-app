package course.gym.repository;

import course.gym.domain.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCourseRepository implements CourseRepository {
  private final JdbcTemplate jdbc;

  public JdbcCourseRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Course> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Course> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Course insert(Course value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByCourseCode(String courseCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
