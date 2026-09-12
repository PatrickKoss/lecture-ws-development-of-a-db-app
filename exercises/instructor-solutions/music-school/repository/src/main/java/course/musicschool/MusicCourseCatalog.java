package course.musicschool;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MusicCourseCatalog {
  private final MusicCourseRepository repository;

  public MusicCourseCatalog(MusicCourseRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<MusicCourse> list() {
    return repository.findAll();
  }

  public Optional<MusicCourse> find(long id) {
    return repository.findById(id);
  }

  public MusicCourse add(MusicCourse value) {
    return repository.insert(value);
  }
}
