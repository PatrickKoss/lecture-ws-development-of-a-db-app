package course.museum;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcExhibitRepositoryTest {
  @Disabled("TODO(B3): Nach der Implementierung aktivieren")
  @Test
  void findsPreparedRows() throws Exception {
    var file = Files.createTempFile("museum-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      var repository = new JdbcExhibitRepository(database);
      assertTrue(repository.findAll().size() >= 3);
      assertEquals("EX-01", repository.findById(1).orElseThrow().inventoryCode());
      assertTrue(repository.findById(99999).isEmpty());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
