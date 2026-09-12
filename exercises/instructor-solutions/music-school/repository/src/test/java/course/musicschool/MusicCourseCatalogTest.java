package course.musicschool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class MusicCourseCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue = new MusicCourse(10L, "K-910", "Startkurs", new BigDecimal("75.00"));
    var repository = new InMemoryMusicCourseRepository(List.of(initialValue));
    var catalog = new MusicCourseCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted = catalog.add(new MusicCourse(null, "K-999", "Testkurs", new BigDecimal("99.00")));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new MusicCourseCatalog(new InMemoryMusicCourseRepository());
    catalog.add(new MusicCourse(null, "K-999", "Testkurs", new BigDecimal("99.00")));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new MusicCourse(null, "K-999", "Anderer Testkurs", new BigDecimal("109.00"))));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new MusicCourseCatalog(new InMemoryMusicCourseRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () -> listed.add(new MusicCourse(null, "K-999", "Testkurs", new BigDecimal("99.00"))));
  }
}
