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
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Medication findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Medication create(MedicationCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
