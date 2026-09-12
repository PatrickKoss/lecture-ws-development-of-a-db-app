package course.vetclinic;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MedicationRepository {
  Optional<Medication> findById(long id) throws SQLException;

  List<Medication> findAll() throws SQLException;

  Medication insert(Medication value) throws SQLException;
}
