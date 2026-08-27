package course.eventtickets.service;

import course.eventtickets.api.CreateVenueRequest;
import course.eventtickets.domain.Venue;
import course.eventtickets.repository.VenueRepository;
import course.eventtickets.web.ConflictException;
import course.eventtickets.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("VENUE_NOT_FOUND", "Spielort nicht gefunden"));
  }

  public Venue create(CreateVenueRequest request) {
    if (repository.existsByVenueCode(request.venueCode()))
      throw new ConflictException(
          "VENUE_VENUE_CODE_EXISTS", "Spielort mit diesem Wert für venue_code existiert bereits");
    return repository.insert(
        new Venue(
            null,
            request.venueCode(),
            request.name(),
            request.street(),
            request.postalCode(),
            request.city(),
            request.capacity()));
  }
}
