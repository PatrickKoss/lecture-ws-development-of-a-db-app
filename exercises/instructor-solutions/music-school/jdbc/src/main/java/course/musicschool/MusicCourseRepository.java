package course.musicschool;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MusicCourseRepository {
  Optional<MusicCourse> findById(long id) throws SQLException;

  List<MusicCourse> findAll() throws SQLException;

  MusicCourse insert(MusicCourse value) throws SQLException;
}
