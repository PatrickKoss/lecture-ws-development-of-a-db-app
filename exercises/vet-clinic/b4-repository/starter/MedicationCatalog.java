package course.vetclinic;
import java.util.List;
public final class MedicationCatalog {
    private final MedicationRepository repository;
    public MedicationCatalog(MedicationRepository repository) { this.repository = repository; }
    public List<Medication> list() { return repository.findAll(); }
}
