package course.cinema.repository;

import course.cinema.domain.Movie;
import java.util.List;
import java.util.Optional;

public interface MovieRepository {
  List<Movie> findAll();

  Optional<Movie> findById(long id);

  Movie insert(Movie value);

  boolean existsByMovieCode(String movieCode);
}
