package course.musicschool.service;

import course.musicschool.domain.MusicCourse;
import course.musicschool.repository.MusicCourseRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MusicCourseService {
  private final MusicCourseRepository repository;

  public MusicCourseService(MusicCourseRepository repository) {
    this.repository = repository;
  }

  public List<MusicCourse> findAll() {
    return repository.findAll();
  }

  public MusicCourse findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException(
                    "MUSIC_COURSE_NOT_FOUND", "Kursangebot nicht gefunden"));
  }

  @Transactional
  public MusicCourse create(MusicCourseCommand command) {
    if (repository.existsByCourseCode(command.courseCode())) {
      throw duplicate();
    }
    return repository.save(
        new MusicCourse(null, command.courseCode(), command.title(), command.fee()));
  }

  @Transactional
  public MusicCourse replace(long id, MusicCourseCommand command) {
    findById(id);
    if (repository.existsByCourseCodeAndIdNot(command.courseCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new MusicCourse(id, command.courseCode(), command.title(), command.fee()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "MUSIC_COURSE_CODE_EXISTS", "course_code ist bereits vergeben");
  }
}
