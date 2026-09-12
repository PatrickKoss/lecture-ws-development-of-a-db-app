package course.bikerental;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class StationCatalog {
  private final StationRepository repository;

  public StationCatalog(StationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Station> list() {
    return repository.findAll();
  }

  public Optional<Station> find(long id) {
    return repository.findById(id);
  }

  public Station add(Station value) {
    return repository.insert(value);
  }
}
