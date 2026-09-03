package course.musicschool;
import java.sql.SQLException;
import java.util.List;
public final class MusicCourseCatalog {
    private final MusicCourseRepository repository;
    public MusicCourseCatalog(MusicCourseRepository repository) { this.repository = repository; }
    public List<MusicCourse> list() throws SQLException { return repository.findAll(); }
}
