package course.eventtickets;
import java.util.List;
public final class VenueCatalog {
    private final VenueRepository repository;
    public VenueCatalog(VenueRepository repository) { this.repository = repository; }
    public List<Venue> list() { return repository.findAll(); }
}
