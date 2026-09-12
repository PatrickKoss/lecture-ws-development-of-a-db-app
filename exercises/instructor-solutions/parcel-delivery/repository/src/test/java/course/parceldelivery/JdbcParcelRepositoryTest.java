package course.parceldelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcParcelRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcParcelRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcParcelRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("PK-01", repository.findById(1).orElseThrow().trackingCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheStoredRow() {
    var candidate = new Parcel(null, "PK-99", "Testempfänger", new BigDecimal("2.5"));

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Parcel(
            inserted.id(), candidate.trackingCode(), candidate.recipient(), candidate.weight()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void translatesAUniqueConstraintFailure() {
    repository.insert(new Parcel(null, "PK-99", "Testempfänger", new BigDecimal("2.50")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            repository.insert(new Parcel(null, "PK-99", "Andere Person", new BigDecimal("3.25"))));
  }
}
