package course.bikerental;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryStationRepository implements StationRepository {
  private final List<Station> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryStationRepository() {}

  public InMemoryStationRepository(List<Station> initialValues) {
    for (Station value : initialValues) {
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
  public synchronized Optional<Station> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Station> findAll() {
    return values.stream().sorted(Comparator.comparing(Station::id)).toList();
  }

  @Override
  public synchronized Station insert(Station value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Station candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.stationCode(), candidate.stationCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The stationCode is already in use");
    }
  }

  private Station withId(Station value, long id) {
    return new Station(
        id, value.stationCode(), value.name(), value.address(), value.capacity(), value.status());
  }
}
