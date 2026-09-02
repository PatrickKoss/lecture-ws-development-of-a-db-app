package course.parceldelivery;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class JdbcParcelRepositoryTest {
  @Disabled("TODO(B3): Nach der Implementierung aktivieren")
  @Test
  void findsPreparedRows() throws Exception {
    var file = Files.createTempFile("parcel-delivery-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      var repository = new JdbcParcelRepository(database);
      assertTrue(repository.findAll().size() >= 3);
      assertEquals("PK-01", repository.findById(1).orElseThrow().trackingCode());
      assertTrue(repository.findById(99999).isEmpty());
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
