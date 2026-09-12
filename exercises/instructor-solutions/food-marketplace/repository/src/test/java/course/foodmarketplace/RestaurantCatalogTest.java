package course.foodmarketplace;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class RestaurantCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue =
        new Restaurant(
            10L,
            "R-910",
            "Startküche",
            "Startweg 1",
            "33602",
            "Bielefeld",
            new BigDecimal("10.0"),
            true);
    var repository = new InMemoryRestaurantRepository(List.of(initialValue));
    var catalog = new RestaurantCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(
            new Restaurant(
                null,
                "R-999",
                "Testküche",
                "Testweg 1",
                "33602",
                "Bielefeld",
                new BigDecimal("12.5"),
                true));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new RestaurantCatalog(new InMemoryRestaurantRepository());
    catalog.add(
        new Restaurant(
            null,
            "R-999",
            "Testküche",
            "Testweg 1",
            "33602",
            "Bielefeld",
            new BigDecimal("12.5"),
            true));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Restaurant(
                    null,
                    "R-999",
                    "Andere Testküche",
                    "Testweg 2",
                    "33604",
                    "Bielefeld",
                    new BigDecimal("15.0"),
                    false)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new RestaurantCatalog(new InMemoryRestaurantRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            listed.add(
                new Restaurant(
                    null,
                    "R-999",
                    "Testküche",
                    "Testweg 1",
                    "33602",
                    "Bielefeld",
                    new BigDecimal("12.5"),
                    true)));
  }
}
