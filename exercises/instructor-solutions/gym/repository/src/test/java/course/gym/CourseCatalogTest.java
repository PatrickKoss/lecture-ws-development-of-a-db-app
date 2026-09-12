package course.gym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class CourseCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new Course(10L, "C-910", "Startkurs", "BEGINNER", 45, null);
    var repository = new InMemoryCourseRepository(List.of(initialValue));
    var catalog = new CourseCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted = catalog.add(new Course(null, "C-999", "Testkurs", "BEGINNER", 45, null));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new CourseCatalog(new InMemoryCourseRepository());
    catalog.add(new Course(null, "C-999", "Testkurs", "BEGINNER", 45, null));

    assertThrows(
        DuplicateKeyException.class,
        () -> catalog.add(new Course(null, "C-999", "Anderer Testkurs", "BEGINNER", 45, null)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new CourseCatalog(new InMemoryCourseRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new Course(null, "C-999", "Testkurs", "BEGINNER", 45, null)));
  }
}
