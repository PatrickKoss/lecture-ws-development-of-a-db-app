package course.cinema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcMovieRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcMovieRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcMovieRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("F-101", repository.findById(1).orElseThrow().movieCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new Movie(null, "F-999", "Testfilm", 2026, 90, "FSK_12", 12);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Movie(
            inserted.id(),
            candidate.movieCode(),
            candidate.title(),
            candidate.releaseYear(),
            candidate.durationMinutes(),
            candidate.fskCode(),
            candidate.minimumAge()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateMovieCode() throws Exception {
    var candidate = new Movie(null, "F-999", "Testfilm", 2026, 90, "FSK_12", 12);
    repository.insert(candidate);
    var duplicateKey = new Movie(null, "F-999", "Anderer Testfilm", 2025, 90, "FSK_6", 6);

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
