package course.gym.service;

import course.gym.api.CreateCourseRequest;
import course.gym.domain.Course;
import course.gym.repository.CourseRepository;
import course.gym.web.ConflictException;
import course.gym.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("COURSE_NOT_FOUND", "Kurs nicht gefunden"));
  }

  public Course create(CreateCourseRequest request) {
    if (repository.existsByCourseCode(request.courseCode()))
      throw new ConflictException(
          "COURSE_COURSE_CODE_EXISTS", "Kurs mit diesem Wert für course_code existiert bereits");
    return repository.insert(
        new Course(
            null,
            request.courseCode(),
            request.title(),
            request.level(),
            request.durationMinutes(),
            request.roomId()));
  }
}
