package course.carworkshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcPartRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcPartRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcPartRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("P-1001", repository.findById(1).orElseThrow().partNumber());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheStoredRow() {
    var candidate =
        new Part(null, "P-9999", "Testteil", "Test", "Z-9", 7, 2, new BigDecimal("19.95"));

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Part(
            inserted.id(),
            candidate.partNumber(),
            candidate.name(),
            candidate.category(),
            candidate.shelfCode(),
            candidate.stockQuantity(),
            candidate.reorderLevel(),
            candidate.listPrice()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void translatesAUniqueConstraintFailure() {
    repository.insert(
        new Part(null, "P-9999", "Testteil", "Test", "Z-9", 7, 2, new BigDecimal("19.95")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            repository.insert(
                new Part(
                    null,
                    "P-9999",
                    "Anderes Testteil",
                    "Andere",
                    "Z-8",
                    4,
                    1,
                    new BigDecimal("8.95"))));
  }
}
