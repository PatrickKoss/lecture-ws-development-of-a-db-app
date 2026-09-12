package course.musicschool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcMusicCourseRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcMusicCourseRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcMusicCourseRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("MU-01", repository.findById(1).orElseThrow().courseCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new MusicCourse(null, "K-999", "Testkurs", new BigDecimal("99"));

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new MusicCourse(
            inserted.id(), candidate.courseCode(), candidate.title(), candidate.fee()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateCourseCode() throws Exception {
    var candidate = new MusicCourse(null, "K-999", "Testkurs", new BigDecimal("99.00"));
    repository.insert(candidate);
    var duplicateKey = new MusicCourse(null, "K-999", "Anderer Testkurs", new BigDecimal("109.00"));

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
