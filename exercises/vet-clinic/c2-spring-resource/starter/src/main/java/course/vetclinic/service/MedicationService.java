package course.vetclinic.service;

import course.vetclinic.api.CreateMedicationRequest;
import course.vetclinic.domain.Medication;
import course.vetclinic.repository.MedicationRepository;
import course.vetclinic.web.ConflictException;
import course.vetclinic.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
            () -> new NotFoundException("MEDICATION_NOT_FOUND", "Medikament nicht gefunden"));
  }

  public Medication create(CreateMedicationRequest request) {
    if (repository.existsByPzn(request.pzn()))
      throw new ConflictException(
          "MEDICATION_PZN_EXISTS", "Medikament mit diesem Wert für pzn existiert bereits");
    return repository.insert(
        new Medication(
            null,
            request.pzn(),
            request.productName(),
            request.activeIngredient(),
            request.dosageForm(),
            request.prescriptionRequired(),
            request.active()));
  }
}
