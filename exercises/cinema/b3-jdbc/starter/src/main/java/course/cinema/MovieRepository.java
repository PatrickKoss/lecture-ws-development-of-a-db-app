package course.cinema;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
  Optional<Movie> findById(long id) throws SQLException;

  List<Movie> findAll() throws SQLException;
}
