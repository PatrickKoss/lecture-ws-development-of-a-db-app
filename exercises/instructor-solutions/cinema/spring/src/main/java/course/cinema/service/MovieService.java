package course.cinema.service;

import course.cinema.domain.Movie;
import course.cinema.repository.MovieRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MovieService {
  private final MovieRepository repository;

  public MovieService(MovieRepository repository) {
    this.repository = repository;
  }

  public List<Movie> findAll() {
    return repository.findAll();
  }

  public Movie findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("MOVIE_NOT_FOUND", "Film nicht gefunden"));
  }

  @Transactional
  public Movie create(MovieCommand command) {
    if (repository.existsByMovieCode(command.movieCode())) {
      throw duplicate();
    }
    return repository.save(
        new Movie(
            null,
            command.movieCode(),
            command.title(),
            command.releaseYear(),
            command.durationMinutes(),
            command.fskCode(),
            command.minimumAge()));
  }

  @Transactional
  public Movie replace(long id, MovieCommand command) {
    findById(id);
    if (repository.existsByMovieCodeAndIdNot(command.movieCode(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Movie(
            id,
            command.movieCode(),
            command.title(),
            command.releaseYear(),
            command.durationMinutes(),
            command.fskCode(),
            command.minimumAge()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException("MOVIE_CODE_EXISTS", "movie_code ist bereits vergeben");
  }
}
