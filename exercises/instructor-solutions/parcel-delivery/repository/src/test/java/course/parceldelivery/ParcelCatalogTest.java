package course.parceldelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class ParcelCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Parcel(10L, "PK-10", "Startempfänger", new BigDecimal("1.50"));
    var repository = new InMemoryParcelRepository(List.of(initialValue));
    var catalog = new ParcelCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted = catalog.add(new Parcel(null, "PK-99", "Testempfänger", new BigDecimal("2.50")));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new ParcelCatalog(new InMemoryParcelRepository());
    catalog.add(new Parcel(null, "PK-99", "Testempfänger", new BigDecimal("2.50")));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new Parcel(null, "PK-99", "Andere Person", new BigDecimal("3.25"))));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new ParcelCatalog(new InMemoryParcelRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Parcel(null, "PK-99", "Testempfänger", new BigDecimal("2.50"))));
  }
}
