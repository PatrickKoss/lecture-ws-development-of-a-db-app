package course.cinema.repository;

import course.cinema.domain.Movie;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMovieRepository implements MovieRepository {
  private final SpringDataMovieRepository data;

  public JpaMovieRepository(SpringDataMovieRepository data) {
    this.data = data;
  }

  @Override
  public List<Movie> findAll() {
    return data.findAll(Sort.by("id")).stream().map(MovieJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Movie> findById(long id) {
    return data.findById(id).map(MovieJpaEntity::toDomain);
  }

  @Override
  public Movie save(Movie value) {
    try {
      return data.saveAndFlush(MovieJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByMovieCode(String value) {
    return data.existsByMovieCode(value);
  }

  @Override
  public boolean existsByMovieCodeAndIdNot(String value, long id) {
    return data.existsByMovieCodeAndIdNot(value, id);
  }

  @Override
  public void deleteById(long id) {
    try {
      data.findById(id)
          .ifPresent(
              found -> {
                data.delete(found);
                data.flush();
              });
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }
}
