package course.hotel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class RoomTypeCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new RoomType(10L, "T10", "Startzimmer", 2, 9900);
    var repository = new InMemoryRoomTypeRepository(List.of(initialValue));
    var catalog = new RoomTypeCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted = catalog.add(new RoomType(null, "TEST", "Testzimmer", 2, 12900));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new RoomTypeCatalog(new InMemoryRoomTypeRepository());
    catalog.add(new RoomType(null, "TEST", "Testzimmer", 2, 12900));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new RoomType(null, "TEST", "Anderes Testzimmer", 3, 14900)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new RoomTypeCatalog(new InMemoryRoomTypeRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new RoomType(null, "TEST", "Testzimmer", 2, 12900)));
  }
}
