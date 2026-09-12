package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
  List<Medication> findAll();

  Optional<Medication> findById(long id);

  Medication save(Medication value);

  boolean existsByPzn(String value);

  boolean existsByPznAndIdNot(String value, long id);

  void deleteById(long id);
}
