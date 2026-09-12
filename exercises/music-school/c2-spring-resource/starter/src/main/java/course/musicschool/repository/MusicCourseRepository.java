package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import java.util.List;
import java.util.Optional;

public interface MusicCourseRepository {
  List<MusicCourse> findAll();

  Optional<MusicCourse> findById(long id);

  MusicCourse save(MusicCourse value);

  boolean existsByCourseCode(String value);

  boolean existsByCourseCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
