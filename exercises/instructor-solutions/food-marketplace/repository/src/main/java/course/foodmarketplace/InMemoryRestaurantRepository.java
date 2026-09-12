package course.foodmarketplace;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryRestaurantRepository implements RestaurantRepository {
  private final List<Restaurant> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryRestaurantRepository() {}

  public InMemoryRestaurantRepository(List<Restaurant> initialValues) {
    for (Restaurant value : initialValues) {
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
  public synchronized Optional<Restaurant> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Restaurant> findAll() {
    return values.stream().sorted(Comparator.comparing(Restaurant::id)).toList();
  }

  @Override
  public synchronized Restaurant insert(Restaurant value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Restaurant candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.partnerNumber(), candidate.partnerNumber()));
    if (duplicate) {
      throw new DuplicateKeyException("The partnerNumber is already in use");
    }
  }

  private Restaurant withId(Restaurant value, long id) {
    return new Restaurant(
        id,
        value.partnerNumber(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.commissionRate(),
        value.active());
  }
}
