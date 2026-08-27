package course.eventtickets.repository;

import course.eventtickets.domain.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVenueRepository implements VenueRepository {
  private final JdbcTemplate jdbc;

  public JdbcVenueRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Venue> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Venue> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Venue insert(Venue value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByVenueCode(String venueCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
