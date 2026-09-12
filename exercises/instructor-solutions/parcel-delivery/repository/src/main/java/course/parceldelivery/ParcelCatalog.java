package course.parceldelivery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ParcelCatalog {
  private final ParcelRepository repository;

  public ParcelCatalog(ParcelRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Parcel> list() {
    return repository.findAll();
  }

  public Optional<Parcel> find(long id) {
    return repository.findById(id);
  }

  public Parcel add(Parcel value) {
    return repository.insert(value);
  }
}
