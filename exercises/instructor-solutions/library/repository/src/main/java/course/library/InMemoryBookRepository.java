package course.library;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class InMemoryBookRepository implements BookRepository {
  private final List<Book> values = new ArrayList<>();
  private long nextId = 1;

  public InMemoryBookRepository() {}

  public InMemoryBookRepository(List<Book> initialValues) {
    for (Book value : initialValues) {
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
  public synchronized Optional<Book> findById(long id) {
    return values.stream().filter(value -> value.id() == id).findFirst();
  }

  @Override
  public synchronized List<Book> findAll() {
    return values.stream().sorted(Comparator.comparing(Book::id)).toList();
  }

  @Override
  public synchronized Book insert(Book value) {
    Objects.requireNonNull(value, "value");
    rejectDuplicateKey(value);
    var inserted = withId(value, nextId++);
    values.add(inserted);
    return inserted;
  }

  private void rejectDuplicateKey(Book candidate) {
    boolean duplicate =
        values.stream().anyMatch(value -> Objects.equals(value.isbn(), candidate.isbn()));
    if (duplicate) {
      throw new DuplicateKeyException("The isbn is already in use");
    }
  }

  private Book withId(Book value, long id) {
    return new Book(
        id,
        value.isbn(),
        value.title(),
        value.publicationYear(),
        value.subjectArea(),
        value.shelfCode());
  }
}
