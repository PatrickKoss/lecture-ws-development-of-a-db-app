package course.musicschool.service;

import course.musicschool.api.CreateMusicCourseRequest;
import course.musicschool.domain.MusicCourse;
import course.musicschool.repository.MusicCourseRepository;
import course.musicschool.web.ConflictException;
import course.musicschool.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
            () -> new NotFoundException("MUSIC_COURSE_NOT_FOUND", "Kursangebot nicht gefunden"));
  }

  public MusicCourse create(CreateMusicCourseRequest request) {
    if (repository.existsByCourseCode(request.courseCode()))
      throw new ConflictException(
          "MUSIC_COURSE_CODE_EXISTS",
          "Kursangebot mit diesem Kurscode existiert bereits");
    return repository.insert(
        new MusicCourse(null, request.courseCode(), request.title(), request.fee()));
  }
}
