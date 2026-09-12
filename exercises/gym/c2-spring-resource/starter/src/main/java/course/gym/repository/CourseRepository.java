package course.gym.repository;

import course.gym.domain.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
  List<Course> findAll();

  Optional<Course> findById(long id);

  Course save(Course value);

  boolean existsByCourseCode(String value);

  boolean existsByCourseCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
