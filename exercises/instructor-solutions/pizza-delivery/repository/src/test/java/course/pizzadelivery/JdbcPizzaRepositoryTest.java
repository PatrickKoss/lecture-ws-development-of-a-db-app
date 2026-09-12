package course.pizzadelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcPizzaRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcPizzaRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcPizzaRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("P-01", repository.findById(1).orElseThrow().pizzaNumber());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheStoredRow() {
    var candidate =
        new Pizza(null, "P-99", "Testpizza", "KLASSIKER", "OFEN-1", new BigDecimal("10.5"), true);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Pizza(
            inserted.id(),
            candidate.pizzaNumber(),
            candidate.name(),
            candidate.category(),
            candidate.ovenStation(),
            candidate.basePrice(),
            candidate.active()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void translatesAUniqueConstraintFailure() {
    repository.insert(
        new Pizza(null, "P-99", "Testpizza", "KLASSIKER", "OFEN-1", new BigDecimal("10.50"), true));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            repository.insert(
                new Pizza(
                    null,
                    "P-99",
                    "Andere Testpizza",
                    "SPEZIAL",
                    "OFEN-2",
                    new BigDecimal("12.00"),
                    false)));
  }
}
