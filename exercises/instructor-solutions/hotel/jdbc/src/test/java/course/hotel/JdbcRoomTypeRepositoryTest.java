package course.hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcRoomTypeRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcRoomTypeRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcRoomTypeRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("EZ", repository.findById(1).orElseThrow().typeCode());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate = new RoomType(null, "TEST", "Testzimmer", 2, 12900);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new RoomType(
            inserted.id(),
            candidate.typeCode(),
            candidate.name(),
            candidate.capacity(),
            candidate.standardPriceCents()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicateTypeCode() throws Exception {
    var candidate = new RoomType(null, "TEST", "Testzimmer", 2, 12900);
    repository.insert(candidate);
    var duplicateKey = new RoomType(null, "TEST", "Anderes Testzimmer", 3, 14900);

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
