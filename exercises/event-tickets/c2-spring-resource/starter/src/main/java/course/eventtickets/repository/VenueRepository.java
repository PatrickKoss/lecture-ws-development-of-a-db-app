package course.eventtickets.repository;

import course.eventtickets.domain.Venue;
import java.util.List;
import java.util.Optional;

public interface VenueRepository {
  List<Venue> findAll();

  Optional<Venue> findById(long id);

  Venue save(Venue value);

  boolean existsByVenueCode(String value);

  boolean existsByVenueCodeAndIdNot(String value, long id);

  void deleteById(long id);
}
