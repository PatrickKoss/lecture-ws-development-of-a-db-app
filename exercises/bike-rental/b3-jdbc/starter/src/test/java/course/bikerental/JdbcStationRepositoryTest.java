package course.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcStationRepositoryTest {
  @Disabled("TODO(B3): Nach der Implementierung aktivieren")
  @Test
  void findsPreparedRows() throws Exception {
    var file = Files.createTempFile("bike-rental-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      var repository = new JdbcStationRepository(database);
      assertTrue(repository.findAll().size() >= 3);
      assertEquals("DOM", repository.findById(1).orElseThrow().stationCode());
      assertTrue(repository.findById(99999).isEmpty());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
