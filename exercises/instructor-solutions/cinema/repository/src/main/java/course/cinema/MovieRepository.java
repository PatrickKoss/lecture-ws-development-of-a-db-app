package course.cinema;

import java.util.List;
import java.util.Optional;

public interface MovieRepository {
  Optional<Movie> findById(long id);

  List<Movie> findAll();

  Movie insert(Movie value);
}
