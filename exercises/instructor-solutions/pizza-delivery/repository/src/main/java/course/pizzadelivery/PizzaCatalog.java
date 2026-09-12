package course.pizzadelivery;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class PizzaCatalog {
  private final PizzaRepository repository;

  public PizzaCatalog(PizzaRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Pizza> list() {
    return repository.findAll();
  }

  public Optional<Pizza> find(long id) {
    return repository.findById(id);
  }

  public Pizza add(Pizza value) {
    return repository.insert(value);
  }
}
