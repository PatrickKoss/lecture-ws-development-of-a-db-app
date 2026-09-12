package course.museum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExhibitCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Exhibit(10L, "INV-910", "Startobjekt", new BigDecimal("250.00"));
    var repository = new InMemoryExhibitRepository(List.of(initialValue));
    var catalog = new ExhibitCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(new Exhibit(null, "INV-999", "Testobjekt", new BigDecimal("500.00")));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new ExhibitCatalog(new InMemoryExhibitRepository());
    catalog.add(new Exhibit(null, "INV-999", "Testobjekt", new BigDecimal("500.00")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Exhibit(null, "INV-999", "Anderes Testobjekt", new BigDecimal("750.00"))));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new ExhibitCatalog(new InMemoryExhibitRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Exhibit(null, "INV-999", "Testobjekt", new BigDecimal("500.00"))));
  }
}
