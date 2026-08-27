package course.gym;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcCourseRepositoryTest {
  @Disabled("TODO(B3): Nach der Implementierung aktivieren")
  @Test
  void findsPreparedRows() throws Exception {
    var file = Files.createTempFile("gym-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      var repository = new JdbcCourseRepository(database);
      assertTrue(repository.findAll().size() >= 3);
      assertEquals("C-101", repository.findById(1).orElseThrow().courseCode());
      assertTrue(repository.findById(99999).isEmpty());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
