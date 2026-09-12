package course.foodmarketplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcRestaurantRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcRestaurantRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcRestaurantRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("R-101", repository.findById(1).orElseThrow().partnerNumber());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate =
        new Restaurant(
            null,
            "R-999",
            "Testküche",
            "Testweg 1",
            "33602",
            "Bielefeld",
            new BigDecimal("12.5"),
            true);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Restaurant(
            inserted.id(),
            candidate.partnerNumber(),
            candidate.name(),
            candidate.street(),
            candidate.postalCode(),
            candidate.city(),
            candidate.commissionRate(),
            candidate.active()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicatePartnerNumber() throws Exception {
    var candidate =
        new Restaurant(
            null,
            "R-999",
            "Testküche",
            "Testweg 1",
            "33602",
            "Bielefeld",
            new BigDecimal("12.5"),
            true);
    repository.insert(candidate);
    var duplicateKey =
        new Restaurant(
            null,
            "R-999",
            "Andere Testküche",
            "Testweg 2",
            "33604",
            "Bielefeld",
            new BigDecimal("15.0"),
            false);

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
