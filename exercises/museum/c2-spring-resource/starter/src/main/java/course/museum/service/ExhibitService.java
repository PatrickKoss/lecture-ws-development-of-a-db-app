package course.museum.service;

import course.museum.domain.Exhibit;
import course.museum.repository.ExhibitRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ExhibitService {
  private final ExhibitRepository repository;

  public ExhibitService(ExhibitRepository repository) {
    this.repository = repository;
  }

  public List<Exhibit> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Exhibit findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Exhibit create(ExhibitCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
