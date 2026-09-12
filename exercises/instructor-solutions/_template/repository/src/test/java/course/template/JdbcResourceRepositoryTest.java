package course.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcResourceRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcResourceRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcResourceRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("R-01", repository.findById(1).orElseThrow().resourceCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheStoredRow() {
    var candidate = new Resource(null, "R-99", "Neue Ressource", new BigDecimal("12.5"));

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Resource(
            inserted.id(), candidate.resourceCode(), candidate.name(), candidate.measure()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void translatesAUniqueConstraintFailure() {
    repository.insert(new Resource(null, "R-99", "Neue Ressource", new BigDecimal("12.50")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            repository.insert(
                new Resource(null, "R-99", "Andere Ressource", new BigDecimal("7.00"))));
  }
}
