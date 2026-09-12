package course.musicschool;

import java.util.List;
import java.util.Optional;

public interface MusicCourseRepository {
  Optional<MusicCourse> findById(long id);

  List<MusicCourse> findAll();

  MusicCourse insert(MusicCourse value);
}
