package course.pizzadelivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryPizzaRepository implements PizzaRepository {
  private final List<Pizza> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryPizzaRepository() {}

  public InMemoryPizzaRepository(List<Pizza> initialValues) {
    for (Pizza value : initialValues) {
      if (value.id() == null) {
        throw new IllegalArgumentException("Initial values need an ID");
      }
      if (findById(value.id()).isPresent()) {
        throw new IllegalArgumentException("Duplicate initial ID " + value.id());
      }
      rejectDuplicateKey(value);
      values.add(value);
      nextId = Math.max(nextId, value.id() + 1);
    }
  }

  @Override
  public synchronized Optional<Pizza> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Pizza> findAll() {
    return values.stream().sorted(Comparator.comparing(Pizza::id)).toList();
  }

  @Override
  public synchronized Pizza insert(Pizza value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Pizza candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.pizzaNumber(), candidate.pizzaNumber()));
    if (duplicate) {
      throw new DuplicateKeyException("The pizzaNumber is already in use");
    }
  }

  private Pizza withId(Pizza value, long id) {
    return new Pizza(
        id,
        value.pizzaNumber(),
        value.name(),
        value.category(),
        value.ovenStation(),
        value.basePrice(),
        value.active());
  }
}
