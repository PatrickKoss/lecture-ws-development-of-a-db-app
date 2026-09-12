package course.carworkshop;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class PartCatalog {
  private final PartRepository repository;

  public PartCatalog(PartRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Part> list() {
    return repository.findAll();
  }

  public Optional<Part> find(long id) {
    return repository.findById(id);
  }

  public Part add(Part value) {
    return repository.insert(value);
  }
}
