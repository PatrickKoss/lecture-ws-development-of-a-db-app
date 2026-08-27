package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMedicationRepository implements MedicationRepository {
  private final JdbcTemplate jdbc;

  public JdbcMedicationRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Medication> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Medication> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Medication insert(Medication value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByPzn(String pzn) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
