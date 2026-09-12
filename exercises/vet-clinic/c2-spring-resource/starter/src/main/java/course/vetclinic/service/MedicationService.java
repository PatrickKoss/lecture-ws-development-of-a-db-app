package course.vetclinic.service;

import course.vetclinic.domain.Medication;
import course.vetclinic.repository.MedicationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MedicationService {
  private final MedicationRepository repository;

  public MedicationService(MedicationRepository repository) {
    this.repository = repository;
  }

  public List<Medication> findAll() {
    return repository.findAll();
  }

  public Medication findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () ->
                new ResourceNotFoundException("MEDICATION_NOT_FOUND", "Medikament nicht gefunden"));
  }

  @Transactional
  public Medication create(MedicationCommand command) {
    if (repository.existsByPzn(command.pzn())) {
      throw duplicate();
    }
    return repository.save(
        new Medication(
            null,
            command.pzn(),
            command.productName(),
            command.activeIngredient(),
            command.dosageForm(),
            command.prescriptionRequired(),
            command.active()));
  }

  @Transactional
  public Medication replace(long id, MedicationCommand command) {
    findById(id);
    if (repository.existsByPznAndIdNot(command.pzn(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Medication(
            id,
            command.pzn(),
            command.productName(),
            command.activeIngredient(),
            command.dosageForm(),
            command.prescriptionRequired(),
            command.active()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("PZN_EXISTS", "pzn ist bereits vergeben");
  }
}
