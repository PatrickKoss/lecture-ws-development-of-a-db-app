package course.eventtickets.service;

import course.eventtickets.domain.Venue;
import course.eventtickets.repository.VenueRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VenueService {
  private final VenueRepository repository;

  public VenueService(VenueRepository repository) {
    this.repository = repository;
  }

  public List<Venue> findAll() {
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Venue findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Venue create(VenueCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
