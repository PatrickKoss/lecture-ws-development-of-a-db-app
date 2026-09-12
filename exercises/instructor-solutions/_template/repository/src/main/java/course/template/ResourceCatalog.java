package course.template;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class ResourceCatalog {
  private final ResourceRepository repository;

  public ResourceCatalog(ResourceRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Resource> list() {
    return repository.findAll();
  }

  public Optional<Resource> find(long id) {
    return repository.findById(id);
  }

  public Resource add(Resource value) {
    return repository.insert(value);
  }
}
