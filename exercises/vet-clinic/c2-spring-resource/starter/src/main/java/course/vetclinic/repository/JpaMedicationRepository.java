package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMedicationRepository implements MedicationRepository {
  private final SpringDataMedicationRepository data;

  public JpaMedicationRepository(SpringDataMedicationRepository data) {
    this.data = data;
  }

  @Override
  public List<Medication> findAll() {
    throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
  }

  @Override
  public Optional<Medication> findById(long id) {
    throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
  }

  @Override
  public Medication save(Medication value) {
    throw new UnsupportedOperationException(
        "TODO C3: mit saveAndFlush speichern und Constraints übersetzen");
  }

  @Override
  public boolean existsByPzn(String value) {
    throw new UnsupportedOperationException("TODO C3: abgeleitete Exists-Methode verwenden");
  }

  @Override
  public boolean existsByPznAndIdNot(String value, long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: Konfliktprüfung für PUT implementieren");
  }

  @Override
  public void deleteById(long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: idempotentes DELETE mit flush implementieren");
  }
}
