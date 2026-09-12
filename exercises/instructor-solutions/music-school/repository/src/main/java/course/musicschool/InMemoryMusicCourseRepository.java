package course.musicschool;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryMusicCourseRepository implements MusicCourseRepository {
  private final List<MusicCourse> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryMusicCourseRepository() {}

  public InMemoryMusicCourseRepository(List<MusicCourse> initialValues) {
    for (MusicCourse value : initialValues) {
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
  public synchronized Optional<MusicCourse> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<MusicCourse> findAll() {
    return values.stream().sorted(Comparator.comparing(MusicCourse::id)).toList();
  }

  @Override
  public synchronized MusicCourse insert(MusicCourse value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(MusicCourse candidate) {
    boolean duplicate =
        values.stream()
            .anyMatch(value -> Objects.equals(value.courseCode(), candidate.courseCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The courseCode is already in use");
    }
  }

  private MusicCourse withId(MusicCourse value, long id) {
    return new MusicCourse(id, value.courseCode(), value.title(), value.fee());
  }
}
