package course.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class StationCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Station(10L, "S10", "Startstation", "Startweg 1", 10, "ACTIVE");
    var repository = new InMemoryStationRepository(List.of(initialValue));
    var catalog = new StationCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(new Station(null, "NEU", "Neue Station", "Testweg 1", 12, "PLANNED"));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new StationCatalog(new InMemoryStationRepository());
    catalog.add(new Station(null, "NEU", "Neue Station", "Testweg 1", 12, "PLANNED"));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new Station(null, "NEU", "Andere Station", "Testweg 2", 20, "ACTIVE")));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new StationCatalog(new InMemoryStationRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Station(null, "NEU", "Neue Station", "Testweg 1", 12, "PLANNED")));
  }
}
