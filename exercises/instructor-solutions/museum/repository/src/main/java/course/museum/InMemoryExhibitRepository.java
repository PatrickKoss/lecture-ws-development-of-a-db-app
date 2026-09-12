package course.museum;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryExhibitRepository implements ExhibitRepository {
  private final List<Exhibit> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryExhibitRepository() {}

  public InMemoryExhibitRepository(List<Exhibit> initialValues) {
    for (Exhibit value : initialValues) {
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
  public synchronized Optional<Exhibit> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Exhibit> findAll() {
    return values.stream().sorted(Comparator.comparing(Exhibit::id)).toList();
  }

  @Override
  public synchronized Exhibit insert(Exhibit value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Exhibit candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.inventoryCode(), candidate.inventoryCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The inventoryCode is already in use");
    }
  }

  private Exhibit withId(Exhibit value, long id) {
    return new Exhibit(id, value.inventoryCode(), value.title(), value.insuredValue());
  }
}
