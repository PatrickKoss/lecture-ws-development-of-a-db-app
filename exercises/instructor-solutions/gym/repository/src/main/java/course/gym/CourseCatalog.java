package course.gym;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class CourseCatalog {
  private final CourseRepository repository;

  public CourseCatalog(CourseRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Course> list() {
    return repository.findAll();
  }

  public Optional<Course> find(long id) {
    return repository.findById(id);
  }

  public Course add(Course value) {
    return repository.insert(value);
  }
}
