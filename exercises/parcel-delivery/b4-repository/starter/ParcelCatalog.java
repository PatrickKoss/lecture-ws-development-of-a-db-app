package course.parceldelivery;
import java.util.List;
public final class ParcelCatalog {
    private final ParcelRepository repository;
    public ParcelCatalog(ParcelRepository repository) { this.repository = repository; }
    public List<Parcel> list() { return repository.findAll(); }
}
