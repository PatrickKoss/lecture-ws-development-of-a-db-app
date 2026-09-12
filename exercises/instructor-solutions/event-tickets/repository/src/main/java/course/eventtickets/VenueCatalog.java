package course.eventtickets;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class VenueCatalog {
  private final VenueRepository repository;

  public VenueCatalog(VenueRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Venue> list() {
    return repository.findAll();
  }

  public Optional<Venue> find(long id) {
    return repository.findById(id);
  }

  public Venue add(Venue value) {
    return repository.insert(value);
  }
}
