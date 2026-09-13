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
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public MusicCourse findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public MusicCourse create(MusicCourseCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
