package course.museum;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ExhibitCatalog {
  private final ExhibitRepository repository;

  public ExhibitCatalog(ExhibitRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Exhibit> list() {
    return repository.findAll();
  }

  public Optional<Exhibit> find(long id) {
    return repository.findById(id);
  }

  public Exhibit add(Exhibit value) {
    return repository.insert(value);
  }
}
