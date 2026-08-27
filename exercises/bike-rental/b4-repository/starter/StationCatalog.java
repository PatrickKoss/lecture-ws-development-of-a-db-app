package course.bikerental;
import java.sql.SQLException;
import java.util.List;
public final class StationCatalog {
    private final StationRepository repository;
    public StationCatalog(StationRepository repository) { this.repository = repository; }
    public List<Station> list() throws SQLException { return repository.findAll(); }
}
