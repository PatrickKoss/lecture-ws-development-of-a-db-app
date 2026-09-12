package course.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class MedicationCatalogTest {
  @Test
  void usesTheRepositoryWithoutDatabaseTypes() {
    var initialValue =
        new Medication(10L, "99999990", "Startmittel", "Startstoff", "Tablette", false, true);
    var repository = new InMemoryMedicationRepository(List.of(initialValue));
    var catalog = new MedicationCatalog(repository);

    assertEquals(List.of(initialValue), catalog.list());
    assertEquals(initialValue, catalog.find(initialValue.id()).orElseThrow());

    var inserted =
        catalog.add(
            new Medication(null, "99999999", "Testmittel", "Teststoff", "Tablette", true, true));
    assertEquals(11L, inserted.id());
    assertEquals(inserted, catalog.find(11L).orElseThrow());
  }

  @Test
  void inMemoryRepositoryRejectsTheBusinessKeyItStores() {
    var catalog = new MedicationCatalog(new InMemoryMedicationRepository());
    catalog.add(
        new Medication(null, "99999999", "Testmittel", "Teststoff", "Tablette", true, true));

    assertThrows(
        DuplicateKeyException.class,
        () ->
            catalog.add(
                new Medication(
                    null,
                    "99999999",
                    "Anderes Testmittel",
                    "Anderer Teststoff",
                    "Kapsel",
                    false,
                    true)));
  }

  @Test
  void catalogReturnsAnImmutableSnapshot() {
    var catalog = new MedicationCatalog(new InMemoryMedicationRepository());
    var listed = catalog.list();

    assertTrue(listed.isEmpty());
    assertThrows(
        UnsupportedOperationException.class,
        () ->
            listed.add(
                new Medication(
                    null, "99999999", "Testmittel", "Teststoff", "Tablette", true, true)));
  }
}
