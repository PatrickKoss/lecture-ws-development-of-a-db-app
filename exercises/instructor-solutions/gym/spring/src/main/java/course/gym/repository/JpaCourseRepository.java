package course.gym.repository;

import course.gym.domain.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCourseRepository implements CourseRepository {
  private final SpringDataCourseRepository data;

  public JpaCourseRepository(SpringDataCourseRepository data) {
    this.data = data;
  }

  @Override
  public List<Course> findAll() {
    return data.findAll(Sort.by("id")).stream().map(CourseJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Course> findById(long id) {
    return data.findById(id).map(CourseJpaEntity::toDomain);
  }

  @Override
  public Course save(Course value) {
    try {
      return data.saveAndFlush(CourseJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByCourseCode(String value) {
    return data.existsByCourseCode(value);
  }

  @Override
  public boolean existsByCourseCodeAndIdNot(String value, long id) {
    return data.existsByCourseCodeAndIdNot(value, id);
  }

  @Override
  public void deleteById(long id) {
    try {
      data.findById(id)
          .ifPresent(
              found -> {
                data.delete(found);
                data.flush();
              });
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }
}
