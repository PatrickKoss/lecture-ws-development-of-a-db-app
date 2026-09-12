package course.hotel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryRoomTypeRepository implements RoomTypeRepository {
  private final List<RoomType> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryRoomTypeRepository() {}

  public InMemoryRoomTypeRepository(List<RoomType> initialValues) {
    for (RoomType value : initialValues) {
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
  public synchronized Optional<RoomType> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<RoomType> findAll() {
    return values.stream().sorted(Comparator.comparing(RoomType::id)).toList();
  }

  @Override
  public synchronized RoomType insert(RoomType value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(RoomType candidate) {
    boolean duplicate =
        values.stream().anyMatch(value -> Objects.equals(value.typeCode(), candidate.typeCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The typeCode is already in use");
    }
  }

  private RoomType withId(RoomType value, long id) {
    return new RoomType(
        id, value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }
}
