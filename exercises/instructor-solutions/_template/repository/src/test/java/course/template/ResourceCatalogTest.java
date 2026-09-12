package course.template;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class ResourceCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Resource(10L, "R-10", "Startressource", new BigDecimal("5.00"));
    var repository = new InMemoryResourceRepository(List.of(initialValue));
    var catalog = new ResourceCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(new Resource(null, "R-99", "Neue Ressource", new BigDecimal("12.50")));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new ResourceCatalog(new InMemoryResourceRepository());
    catalog.add(new Resource(null, "R-99", "Neue Ressource", new BigDecimal("12.50")));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new Resource(null, "R-99", "Andere Ressource", new BigDecimal("7.00"))));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new ResourceCatalog(new InMemoryResourceRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Resource(null, "R-99", "Neue Ressource", new BigDecimal("12.50"))));
  }
}
