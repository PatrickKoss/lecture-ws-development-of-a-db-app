package course.bikerental;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface StationRepository {
  Optional<Station> findById(long id) throws SQLException;

  List<Station> findAll() throws SQLException;

  Station insert(Station value) throws SQLException;
}
