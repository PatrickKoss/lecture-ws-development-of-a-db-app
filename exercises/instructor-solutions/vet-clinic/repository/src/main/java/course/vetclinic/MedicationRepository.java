package course.vetclinic;

import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
  Optional<Medication> findById(long id);

  List<Medication> findAll();

  Medication insert(Medication value);
}
