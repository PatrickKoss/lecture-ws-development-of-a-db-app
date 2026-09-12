package course.hotel;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class RoomTypeCatalog {
  private final RoomTypeRepository repository;

  public RoomTypeCatalog(RoomTypeRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<RoomType> list() {
    return repository.findAll();
  }

  public Optional<RoomType> find(long id) {
    return repository.findById(id);
  }

  public RoomType add(RoomType value) {
    return repository.insert(value);
  }
}
