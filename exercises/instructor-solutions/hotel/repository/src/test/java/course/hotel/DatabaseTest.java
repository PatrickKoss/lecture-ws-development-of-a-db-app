package course.hotel;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import org.junit.jupiter.api.Test;

class DatabaseTest {
  @Test
  void enablesForeignKeysForEveryConnection() throws Exception {
    var file = Files.createTempFile("hotel-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try (var connection = database.open();
        var rows = connection.createStatement().executeQuery("PRAGMA foreign_keys")) {
      assertTrue(rows.next());
      assertEquals(1, rows.getInt(1));
    } finally {
      Files.deleteIfExists(file);
    }
  }

  @Test
  void preparedLookupRepositoryReadsDomainData() throws Exception {
    var file = Files.createTempFile("hotel-", ".db");
    var database = new Database("jdbc:sqlite:" + file);
    database.initialize();
    try {
      assertTrue(new JdbcLookupRepository(database).findAllLabels().size() >= 3);
    } finally {
      Files.deleteIfExists(file);
    }
  }
}
