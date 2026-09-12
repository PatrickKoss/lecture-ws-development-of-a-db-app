package course.gym;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
  Optional<Course> findById(long id);

  List<Course> findAll();

  Course insert(Course value);
}
