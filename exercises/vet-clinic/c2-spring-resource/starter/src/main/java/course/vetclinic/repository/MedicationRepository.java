package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
  List<Medication> findAll();

  Optional<Medication> findById(long id);

  Medication insert(Medication value);

  boolean existsByPzn(String pzn);
}
