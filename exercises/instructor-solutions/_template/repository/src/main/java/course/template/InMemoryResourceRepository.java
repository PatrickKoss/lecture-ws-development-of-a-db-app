package course.template;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryResourceRepository implements ResourceRepository {
  private final List<Resource> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryResourceRepository() {}

  public InMemoryResourceRepository(List<Resource> initialValues) {
    for (Resource value : initialValues) {
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
  public synchronized Optional<Resource> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Resource> findAll() {
    return values.stream().sorted(Comparator.comparing(Resource::id)).toList();
  }

  @Override
  public synchronized Resource insert(Resource value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Resource candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.resourceCode(), candidate.resourceCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The resourceCode is already in use");
    }
  }

  private Resource withId(Resource value, long id) {
    return new Resource(id, value.resourceCode(), value.name(), value.measure());
  }
}
