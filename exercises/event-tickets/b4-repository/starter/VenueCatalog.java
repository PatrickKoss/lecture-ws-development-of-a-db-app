package course.eventtickets;
import java.sql.SQLException;
import java.util.List;
public final class VenueCatalog {
    private final VenueRepository repository;
    public VenueCatalog(VenueRepository repository) { this.repository = repository; }
    public List<Venue> list() throws SQLException { return repository.findAll(); }
}
