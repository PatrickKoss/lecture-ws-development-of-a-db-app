package course.eventtickets;

import java.util.List;
import java.util.Optional;

public interface VenueRepository {
  Optional<Venue> findById(long id);

  List<Venue> findAll();

  Venue insert(Venue value);
}
