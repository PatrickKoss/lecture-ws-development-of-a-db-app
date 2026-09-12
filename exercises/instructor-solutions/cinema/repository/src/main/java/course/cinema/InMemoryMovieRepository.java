package course.cinema;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryMovieRepository implements MovieRepository {
  private final List<Movie> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryMovieRepository() {}

  public InMemoryMovieRepository(List<Movie> initialValues) {
    for (Movie value : initialValues) {
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
  public synchronized Optional<Movie> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Movie> findAll() {
    return values.stream().sorted(Comparator.comparing(Movie::id)).toList();
  }

  @Override
  public synchronized Movie insert(Movie value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Movie candidate) {
    boolean duplicate =
        values.stream().anyMatch(value -> Objects.equals(value.movieCode(), candidate.movieCode()));
    if (duplicate) {
      throw new DuplicateKeyException("The movieCode is already in use");
    }
  }

  private Movie withId(Movie value, long id) {
    return new Movie(
        id,
        value.movieCode(),
        value.title(),
        value.releaseYear(),
        value.durationMinutes(),
        value.fskCode(),
        value.minimumAge());
  }
}
