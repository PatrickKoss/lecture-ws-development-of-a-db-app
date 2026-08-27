package course.carworkshop;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PartRepository {
  Optional<Part> findById(long id) throws SQLException;

  List<Part> findAll() throws SQLException;
}
