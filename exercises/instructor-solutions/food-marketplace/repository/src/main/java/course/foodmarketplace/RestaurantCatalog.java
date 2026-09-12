package course.foodmarketplace;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class RestaurantCatalog {
  private final RestaurantRepository repository;

  public RestaurantCatalog(RestaurantRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Restaurant> list() {
    return repository.findAll();
  }

  public Optional<Restaurant> find(long id) {
    return repository.findById(id);
  }

  public Restaurant add(Restaurant value) {
    return repository.insert(value);
  }
}
