package course.cinema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class MovieCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Movie(10L, "F-910", "Startfilm", 2024, 95, "FSK_6", 6);
    var repository = new InMemoryMovieRepository(List.of(initialValue));
    var catalog = new MovieCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted = catalog.add(new Movie(null, "F-999", "Testfilm", 2026, 90, "FSK_12", 12));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new MovieCatalog(new InMemoryMovieRepository());
    catalog.add(new Movie(null, "F-999", "Testfilm", 2026, 90, "FSK_12", 12));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new Movie(null, "F-999", "Anderer Testfilm", 2025, 90, "FSK_6", 6)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new MovieCatalog(new InMemoryMovieRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Movie(null, "F-999", "Testfilm", 2026, 90, "FSK_12", 12)));
  }
}
