package course.eventtickets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcVenueRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcVenueRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcVenueRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("V-01", repository.findById(1).orElseThrow().venueCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheStoredRow() {
    var candidate = new Venue(null, "V-99", "Testhalle", "Testweg 1", "33602", "Bielefeld", 250);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Venue(
            inserted.id(),
            candidate.venueCode(),
            candidate.name(),
            candidate.street(),
            candidate.postalCode(),
            candidate.city(),
            candidate.capacity()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void translatesAUniqueConstraintFailure() {
    repository.insert(new Venue(null, "V-99", "Testhalle", "Testweg 1", "33602", "Bielefeld", 250));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            repository.insert(
                new Venue(
                    null, "V-99", "Andere Testhalle", "Testweg 2", "33604", "Bielefeld", 400)));
  }
}
