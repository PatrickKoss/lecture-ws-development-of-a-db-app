package course.gym.service;

import course.gym.domain.Course;
import course.gym.repository.CourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CourseService {
  private final CourseRepository repository;

  public CourseService(CourseRepository repository) {
    this.repository = repository;
  }

  public List<Course> findAll() {
    return repository.findAll();
  }

  public Course findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("COURSE_NOT_FOUND", "Kurs nicht gefunden"));
  }

  @Transactional
  public Course create(CourseCommand command) {
    if (repository.existsByCourseCode(command.courseCode())) {
      throw duplicate();
    }
    return repository.save(
        new Course(
            null,
            command.courseCode(),
            command.title(),
            command.level(),
            command.durationMinutes(),
            command.roomId()));
  }

  @Transactional
  public Course replace(long id, CourseCommand command) {
    findById(id);
    if (repository.existsByCourseCodeAndIdNot(command.courseCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Course(
            id,
            command.courseCode(),
            command.title(),
            command.level(),
            command.durationMinutes(),
            command.roomId()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("COURSE_CODE_EXISTS", "course_code ist bereits vergeben");
  }
}
