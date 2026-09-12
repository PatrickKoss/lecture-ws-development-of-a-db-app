package course.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcBookRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcBookRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcBookRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("9783446279899", repository.findById(1).orElseThrow().isbn());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new Book(null, "9780000000002", "Testbuch", 2026, "Informatik", "Z-9");

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Book(
            inserted.id(),
            candidate.isbn(),
            candidate.title(),
            candidate.publicationYear(),
            candidate.subjectArea(),
            candidate.shelfCode()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateIsbn() throws Exception {
    var candidate = new Book(null, "9780000000002", "Testbuch", 2026, "Informatik", "Z-9");
    repository.insert(candidate);
    var duplicateKey =
        new Book(null, "9780000000002", "Anderes Testbuch", 2025, "Geschichte", "Z-8");

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
