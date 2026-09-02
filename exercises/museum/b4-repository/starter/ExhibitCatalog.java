package course.museum;
import java.sql.SQLException;
import java.util.List;
public final class ExhibitCatalog {
    private final ExhibitRepository repository;
    public ExhibitCatalog(ExhibitRepository repository) { this.repository = repository; }
    public List<Exhibit> list() throws SQLException { return repository.findAll(); }
}
