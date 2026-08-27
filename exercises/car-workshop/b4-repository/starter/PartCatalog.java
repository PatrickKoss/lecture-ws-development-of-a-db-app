package course.carworkshop;
import java.sql.SQLException;
import java.util.List;
public final class PartCatalog {
    private final PartRepository repository;
    public PartCatalog(PartRepository repository) { this.repository = repository; }
    public List<Part> list() throws SQLException { return repository.findAll(); }
}
