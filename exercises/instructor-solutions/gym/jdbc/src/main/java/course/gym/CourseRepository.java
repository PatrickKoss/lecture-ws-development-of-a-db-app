package course.gym;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
  Optional<Course> findById(long id) throws SQLException;

  List<Course> findAll() throws SQLException;

  Course insert(Course value) throws SQLException;
}
