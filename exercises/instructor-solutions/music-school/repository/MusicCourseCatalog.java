package course.musicschool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class MusicCourseCatalog {
  private final MusicCourseRepository repository;

  public MusicCourseCatalog(MusicCourseRepository repository) {
    this.repository = repository;
  }

  public List<MusicCourse> list() throws SQLException {
    return repository.findAll();
  }

  public Optional<MusicCourse> find(long id) throws SQLException {
    return repository.findById(id);
  }
}
