package course.cinema;
import java.sql.SQLException;
import java.util.List;
public final class MovieCatalog {
    private final MovieRepository repository;
    public MovieCatalog(MovieRepository repository) { this.repository = repository; }
    public List<Movie> list() throws SQLException { return repository.findAll(); }
}
