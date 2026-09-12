package course.parceldelivery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryParcelRepository implements ParcelRepository {
  private final List<Parcel> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryParcelRepository() {}

  public InMemoryParcelRepository(List<Parcel> initialValues) {
    for (Parcel value : initialValues) {
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
  public synchronized Optional<Parcel> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Parcel> findAll() {
    return values.stream().sorted(Comparator.comparing(Parcel::id)).toList();
  }

  @Override
  public synchronized Parcel insert(Parcel value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Parcel candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.trackingCode(), candidate.trackingCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The trackingCode is already in use");
    }
  }

  private Parcel withId(Parcel value, long id) {
    return new Parcel(id, value.trackingCode(), value.recipient(), value.weight());
  }
}
