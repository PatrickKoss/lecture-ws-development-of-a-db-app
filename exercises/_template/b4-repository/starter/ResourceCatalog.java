package course.template;
import java.sql.SQLException;
import java.util.List;
public final class ResourceCatalog {
    private final ResourceRepository repository;
    public ResourceCatalog(ResourceRepository repository) { this.repository = repository; }
    public List<Resource> list() throws SQLException { return repository.findAll(); }
}
