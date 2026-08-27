package course.cinema.repository;

import course.cinema.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcMovieRepository implements MovieRepository {
  private final JdbcTemplate jdbc;

  public JdbcMovieRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Movie> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Movie> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Movie insert(Movie value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByMovieCode(String movieCode) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
