package course.template;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ResourceRepository {
  Optional<Resource> findById(long id) throws SQLException;

  List<Resource> findAll() throws SQLException;
}
