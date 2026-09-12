package course.museum;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ExhibitRepository {
  Optional<Exhibit> findById(long id) throws SQLException;

  List<Exhibit> findAll() throws SQLException;

  Exhibit insert(Exhibit value) throws SQLException;
}
