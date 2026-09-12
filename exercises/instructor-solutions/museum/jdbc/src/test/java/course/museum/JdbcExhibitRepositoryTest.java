package course.museum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcExhibitRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcExhibitRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcExhibitRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("EX-01", repository.findById(1).orElseThrow().inventoryCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new Exhibit(null, "INV-999", "Testobjekt", new BigDecimal("500"));

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Exhibit(
            inserted.id(),
            candidate.inventoryCode(),
            candidate.title(),
            candidate.insuredValue()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateInventoryCode() throws Exception {
    var candidate = new Exhibit(null, "INV-999", "Testobjekt", new BigDecimal("500.00"));
    repository.insert(candidate);
    var duplicateKey = new Exhibit(null, "INV-999", "Anderes Testobjekt", new BigDecimal("750.00"));

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
