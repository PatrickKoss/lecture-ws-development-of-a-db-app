package course.carworkshop;
import java.util.List;
public final class PartCatalog {
    private final PartRepository repository;
    public PartCatalog(PartRepository repository) { this.repository = repository; }
    public List<Part> list() { return repository.findAll(); }
}
