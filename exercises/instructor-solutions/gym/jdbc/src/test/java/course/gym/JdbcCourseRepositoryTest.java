package course.gym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcCourseRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcCourseRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcCourseRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("C-101", repository.findById(1).orElseThrow().courseCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new Course(null, "C-999", "Testkurs", "BEGINNER", 45, null);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Course(
            inserted.id(),
            candidate.courseCode(),
            candidate.title(),
            candidate.level(),
            candidate.durationMinutes(),
            candidate.roomId()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateCourseCode() throws Exception {
    var candidate = new Course(null, "C-999", "Testkurs", "BEGINNER", 45, null);
    repository.insert(candidate);
    var duplicateKey = new Course(null, "C-999", "Anderer Testkurs", "BEGINNER", 45, null);

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
