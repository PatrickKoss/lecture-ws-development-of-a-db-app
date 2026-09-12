package course.eventtickets;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface VenueRepository {
  Optional<Venue> findById(long id) throws SQLException;

  List<Venue> findAll() throws SQLException;

  Venue insert(Venue value) throws SQLException;
}
