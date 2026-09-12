package course.gym;
import java.util.List;
public final class CourseCatalog {
    private final CourseRepository repository;
    public CourseCatalog(CourseRepository repository) { this.repository = repository; }
    public List<Course> list() { return repository.findAll(); }
}
