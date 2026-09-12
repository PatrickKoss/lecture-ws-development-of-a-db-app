package course.carworkshop;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class PartCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue =
        new Part(10L, "P-9010", "Startteil", "Test", "Z-1", 3, 1, new BigDecimal("4.50"));
    var repository = new InMemoryPartRepository(List.of(initialValue));
    var catalog = new PartCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(
            new Part(null, "P-9999", "Testteil", "Test", "Z-9", 7, 2, new BigDecimal("19.95")));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new PartCatalog(new InMemoryPartRepository());
    catalog.add(new Part(null, "P-9999", "Testteil", "Test", "Z-9", 7, 2, new BigDecimal("19.95")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Part(
                    null,
                    "P-9999",
                    "Anderes Testteil",
                    "Andere",
                    "Z-8",
                    4,
                    1,
                    new BigDecimal("8.95"))));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new PartCatalog(new InMemoryPartRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            listed.add(
                new Part(
                    null, "P-9999", "Testteil", "Test", "Z-9", 7, 2, new BigDecimal("19.95"))));
  }
}
