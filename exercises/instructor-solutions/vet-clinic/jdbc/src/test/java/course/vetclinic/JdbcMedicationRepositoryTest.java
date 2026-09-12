package course.vetclinic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JdbcMedicationRepositoryTest {
  @TempDir Path temporaryDirectory;

  private JdbcMedicationRepository repository;

  @BeforeEach
  void initializeDatabase() throws Exception {
    var database = new Database("jdbc:sqlite:" + temporaryDirectory.resolve("test.db"));
    database.initialize();
    repository = new JdbcMedicationRepository(database);
  }

  @Test
  void readsRowsWithExplicitColumnMapping() throws Exception {
    var values = repository.findAll();

    assertTrue(values.size() >= 3);
    assertEquals("09231122", repository.findById(1).orElseThrow().pzn());
    assertTrue(repository.findById(99999).isEmpty());
    assertTrue(values.getFirst().id() < values.getLast().id());
  }

  @Test
  void insertsAndReturnsTheGeneratedId() throws Exception {
    var candidate =
        new Medication(null, "99999999", "Testmittel", "Teststoff", "Tablette", true, true);

    var inserted = repository.insert(candidate);

    assertTrue(inserted.id() > 0);
    assertEquals(
        new Medication(
            inserted.id(),
            candidate.pzn(),
            candidate.productName(),
            candidate.activeIngredient(),
            candidate.dosageForm(),
            candidate.prescriptionRequired(),
            candidate.active()),
        inserted);
    assertEquals(inserted, repository.findById(inserted.id()).orElseThrow());
  }

  @Test
  void rejectsADuplicatePzn() throws Exception {
    var candidate =
        new Medication(null, "99999999", "Testmittel", "Teststoff", "Tablette", true, true);
    repository.insert(candidate);
    var duplicateKey =
        new Medication(
            null, "99999999", "Anderes Testmittel", "Anderer Teststoff", "Kapsel", false, true);

    assertThrows(SQLException.class, () -> repository.insert(duplicateKey));
  }
}
