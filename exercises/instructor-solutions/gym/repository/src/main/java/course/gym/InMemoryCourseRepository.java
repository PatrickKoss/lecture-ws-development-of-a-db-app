package course.gym;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryCourseRepository implements CourseRepository {
  private final List<Course> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryCourseRepository() {}

  public InMemoryCourseRepository(List<Course> initialValues) {
    for (Course value : initialValues) {
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
  public synchronized Optional<Course> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Course> findAll() {
    return values.stream().sorted(Comparator.comparing(Course::id)).toList();
  }

  @Override
  public synchronized Course insert(Course value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Course candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.courseCode(), candidate.courseCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The courseCode is already in use");
    }
  }

  private Course withId(Course value, long id) {
    return new Course(
        id,
        value.courseCode(),
        value.title(),
        value.level(),
        value.durationMinutes(),
        value.roomId());
  }
}
