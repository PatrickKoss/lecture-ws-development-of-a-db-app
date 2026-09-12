package course.eventtickets;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryVenueRepository implements VenueRepository {
  private final List<Venue> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryVenueRepository() {}

  public InMemoryVenueRepository(List<Venue> initialValues) {
    for (Venue value : initialValues) {
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
  public synchronized Optional<Venue> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Venue> findAll() {
    return values.stream().sorted(Comparator.comparing(Venue::id)).toList();
  }

  @Override
  public synchronized Venue insert(Venue value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Venue candidate) {
    boolean duplicate =
        values.stream().anyMatch(value -> Objects.equals(value.venueCode(), candidate.venueCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The venueCode is already in use");
    }
  }

  private Venue withId(Venue value, long id) {
    return new Venue(
        id,
        value.venueCode(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.capacity());
  }
}
