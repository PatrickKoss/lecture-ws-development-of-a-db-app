package course.parceldelivery;
import java.sql.SQLException;
import java.util.List;
public final class ParcelCatalog {
    private final ParcelRepository repository;
    public ParcelCatalog(ParcelRepository repository) { this.repository = repository; }
    public List<Parcel> list() throws SQLException { return repository.findAll(); }
}
