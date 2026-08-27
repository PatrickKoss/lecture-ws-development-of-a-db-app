package course.gym.repository;

import course.gym.domain.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
  List<Course> findAll();

  Optional<Course> findById(long id);

  Course insert(Course value);

  boolean existsByCourseCode(String courseCode);
}
