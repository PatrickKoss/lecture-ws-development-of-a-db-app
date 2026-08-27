package course.vetclinic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public final class JdbcMedicationRepository implements MedicationRepository {
  private final Database database;

  public JdbcMedicationRepository(Database database) {
    this.database = database;
  }

  @Override
  public Optional<Medication> findById(long id) throws SQLException {
    // TODO(B3): PreparedStatement ausführen und genau eine Zeile mappen.
    throw new UnsupportedOperationException("TODO B3 findById");
  }

  @Override
  public List<Medication> findAll() throws SQLException {
    // TODO(B3): Alle medications sortiert lesen.
    throw new UnsupportedOperationException("TODO B3 findAll");
  }

  private Medication map(ResultSet row) throws SQLException {
    // TODO(B3): pzn, product_name und prescription_required den Feldern
    // pzn, productName, activeIngredient, dosageForm, prescriptionRequired, active zuordnen.
    throw new UnsupportedOperationException("TODO B3 mapping");
  }
}
