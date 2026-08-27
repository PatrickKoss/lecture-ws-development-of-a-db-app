package course.cinema.service;

import course.cinema.api.CreateMovieRequest;
import course.cinema.domain.Movie;
import course.cinema.repository.MovieRepository;
import course.cinema.web.ConflictException;
import course.cinema.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
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
        .orElseThrow(() -> new NotFoundException("MOVIE_NOT_FOUND", "Film nicht gefunden"));
  }

  public Movie create(CreateMovieRequest request) {
    if (repository.existsByMovieCode(request.movieCode()))
      throw new ConflictException(
          "MOVIE_MOVIE_CODE_EXISTS", "Film mit diesem Wert für movie_code existiert bereits");
    return repository.insert(
        new Movie(
            null,
            request.movieCode(),
            request.title(),
            request.releaseYear(),
            request.durationMinutes(),
            request.fskCode(),
            request.minimumAge()));
  }
}
