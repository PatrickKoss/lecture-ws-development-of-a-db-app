package course.pizzadelivery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class PizzaCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue =
        new Pizza(10L, "P-90", "Startpizza", "KLASSIKER", "OFEN-1", new BigDecimal("9.00"), true);
    var repository = new InMemoryPizzaRepository(List.of(initialValue));
    var catalog = new PizzaCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(
            new Pizza(
                null, "P-99", "Testpizza", "KLASSIKER", "OFEN-1", new BigDecimal("10.50"), true));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new PizzaCatalog(new InMemoryPizzaRepository());
    catalog.add(
        new Pizza(null, "P-99", "Testpizza", "KLASSIKER", "OFEN-1", new BigDecimal("10.50"), true));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Pizza(
                    null,
                    "P-99",
                    "Andere Testpizza",
                    "SPEZIAL",
                    "OFEN-2",
                    new BigDecimal("12.00"),
                    false)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new PizzaCatalog(new InMemoryPizzaRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            listed.add(
                new Pizza(
                    null,
                    "P-99",
                    "Testpizza",
                    "KLASSIKER",
                    "OFEN-1",
                    new BigDecimal("10.50"),
                    true)));
  }
}
