package course.bikerental;
import java.util.List;
public final class StationCatalog {
    private final StationRepository repository;
    public StationCatalog(StationRepository repository) { this.repository = repository; }
    public List<Station> list() { return repository.findAll(); }
}
