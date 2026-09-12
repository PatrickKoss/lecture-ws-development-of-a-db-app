package course.carworkshop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryPartRepository implements PartRepository {
  private final List<Part> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryPartRepository() {}

  public InMemoryPartRepository(List<Part> initialValues) {
    for (Part value : initialValues) {
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
  public synchronized Optional<Part> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Part> findAll() {
    return values.stream().sorted(Comparator.comparing(Part::id)).toList();
  }

  @Override
  public synchronized Part insert(Part value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Part candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.partNumber(), candidate.partNumber()));
    if (duplicate) {
      throw new DuplicateKeyException("The partNumber is already in use");
    }
  }

  private Part withId(Part value, long id) {
    return new Part(
        id,
        value.partNumber(),
        value.name(),
        value.category(),
        value.shelfCode(),
        value.stockQuantity(),
        value.reorderLevel(),
        value.listPrice());
  }
}
