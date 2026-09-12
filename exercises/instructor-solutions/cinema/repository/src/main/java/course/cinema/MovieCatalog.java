package course.cinema;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class MovieCatalog {
  private final MovieRepository repository;

  public MovieCatalog(MovieRepository repository) {
    this.repository = Objects.requireNonNull(repository, "repository");
  }

  public List<Movie> list() {
    return repository.findAll();
  }

  public Optional<Movie> find(long id) {
    return repository.findById(id);
  }

  public Movie add(Movie value) {
    return repository.insert(value);
  }
}
