package course.vetclinic;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MedicationCatalog {
  private final MedicationRepository repository;

  public MedicationCatalog(MedicationRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Medication> list() {
    return repository.findAll();
  }

  public Optional<Medication> find(long id) {
    return repository.findById(id);
  }

  public Medication add(Medication value) {
    return repository.insert(value);
  }
}
