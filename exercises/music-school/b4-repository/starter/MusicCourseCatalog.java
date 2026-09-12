package course.musicschool;
import java.util.List;
public final class MusicCourseCatalog {
    private final MusicCourseRepository repository;
    public MusicCourseCatalog(MusicCourseRepository repository) { this.repository = repository; }
    public List<MusicCourse> list() { return repository.findAll(); }
}
