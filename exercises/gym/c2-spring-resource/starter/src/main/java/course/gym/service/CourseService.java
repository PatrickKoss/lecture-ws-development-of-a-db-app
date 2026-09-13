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
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Course findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Course create(CourseCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
