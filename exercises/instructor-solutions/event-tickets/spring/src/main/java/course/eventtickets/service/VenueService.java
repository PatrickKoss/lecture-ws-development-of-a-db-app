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
    return repository.findAll();
  }

  public Venue findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("VENUE_NOT_FOUND", "Spielort nicht gefunden"));
  }

  @Transactional
  public Venue create(VenueCommand command) {
    if (repository.existsByVenueCode(command.venueCode())) {
      throw duplicate();
    }
    return repository.save(
        new Venue(
            null,
            command.venueCode(),
            command.name(),
            command.street(),
            command.postalCode(),
            command.city(),
            command.capacity()));
  }

  @Transactional
  public Venue replace(long id, VenueCommand command) {
    findById(id);
    if (repository.existsByVenueCodeAndIdNot(command.venueCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Venue(
            id,
            command.venueCode(),
            command.name(),
            command.street(),
            command.postalCode(),
            command.city(),
            command.capacity()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("VENUE_CODE_EXISTS", "venue_code ist bereits vergeben");
  }
}
