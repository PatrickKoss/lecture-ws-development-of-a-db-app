package course.hotel;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcRoomTypeRepositoryTest {
  @Disabled("TODO(B3): Nach der Implementierung aktivieren")
  @Test
  void findsPreparedRows() throws Exception {
    var file = Files.createTempFile("hotel-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      var repository = new JdbcRoomTypeRepository(database);
      assertTrue(repository.findAll().size() >= 3);
      assertEquals("EZ", repository.findById(1).orElseThrow().typeCode());
      assertTrue(repository.findById(99999).isEmpty());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
