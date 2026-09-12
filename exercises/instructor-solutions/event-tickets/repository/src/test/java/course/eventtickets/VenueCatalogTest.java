package course.eventtickets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class VenueCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue =
        new Venue(10L, "V-10", "Starthalle", "Startweg 1", "33602", "Bielefeld", 100);
    var repository = new InMemoryVenueRepository(List.of(initialValue));
    var catalog = new VenueCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(new Venue(null, "V-99", "Testhalle", "Testweg 1", "33602", "Bielefeld", 250));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new VenueCatalog(new InMemoryVenueRepository());
    catalog.add(new Venue(null, "V-99", "Testhalle", "Testweg 1", "33602", "Bielefeld", 250));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Venue(
                    null, "V-99", "Andere Testhalle", "Testweg 2", "33604", "Bielefeld", 400)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new VenueCatalog(new InMemoryVenueRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            listed.add(
                new Venue(null, "V-99", "Testhalle", "Testweg 1", "33602", "Bielefeld", 250)));
  }
}
