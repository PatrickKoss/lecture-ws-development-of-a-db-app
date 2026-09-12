package course.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class BookCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Book(10L, "9780000000019", "Startbuch", 2024, "Informatik", "Z-1");
    var repository = new InMemoryBookRepository(List.of(initialValue));
    var catalog = new BookCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(new Book(null, "9780000000002", "Testbuch", 2026, "Informatik", "Z-9"));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new BookCatalog(new InMemoryBookRepository());
    catalog.add(new Book(null, "9780000000002", "Testbuch", 2026, "Informatik", "Z-9"));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Book(null, "9780000000002", "Anderes Testbuch", 2025, "Geschichte", "Z-8")));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new BookCatalog(new InMemoryBookRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Book(null, "9780000000002", "Testbuch", 2026, "Informatik", "Z-9")));
  }
}
