package course.vetclinic;
import java.sql.SQLException;
import java.util.List;
public final class MedicationCatalog {
    private final MedicationRepository repository;
    public MedicationCatalog(MedicationRepository repository) { this.repository = repository; }
    public List<Medication> list() throws SQLException { return repository.findAll(); }
}
