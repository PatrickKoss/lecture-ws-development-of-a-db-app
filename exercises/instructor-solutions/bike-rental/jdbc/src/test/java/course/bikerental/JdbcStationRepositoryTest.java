package course.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcStationRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcStationRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcStationRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("DOM", repository.findById(1).orElseThrow().stationCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new Station(null, "NEU", "Neue Station", "Testweg 1", 12, "PLANNED");

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Station(
            inserted.id(),
            candidate.stationCode(),
            candidate.name(),
            candidate.address(),
            candidate.capacity(),
            candidate.status()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateStationCode() throws Exception {
    var candidate = new Station(null, "NEU", "Neue Station", "Testweg 1", 12, "PLANNED");
    repository.insert(candidate);
    var duplicateKey = new Station(null, "NEU", "Andere Station", "Testweg 2", 20, "ACTIVE");

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
