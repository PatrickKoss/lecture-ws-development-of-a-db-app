package course.musicschool.repository;

import course.musicschool.domain.MusicCourse;
import java.util.List;
import java.util.Optional;

public interface MusicCourseRepository {
  List<MusicCourse> findAll();

  Optional<MusicCourse> findById(long id);

  MusicCourse insert(MusicCourse value);

  boolean existsByCourseCode(String courseCode);
}
