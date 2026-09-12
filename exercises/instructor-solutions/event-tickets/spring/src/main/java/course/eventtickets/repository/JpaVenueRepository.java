package course.eventtickets.repository;

import course.eventtickets.domain.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaVenueRepository implements VenueRepository {
  private final SpringDataVenueRepository data;

  public JpaVenueRepository(SpringDataVenueRepository data) {
    this.data = data;
  }

  @Override
  public List<Venue> findAll() {
    return data.findAll(Sort.by("id")).stream().map(VenueJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Venue> findById(long id) {
    return data.findById(id).map(VenueJpaEntity::toDomain);
  }

  @Override
  public Venue save(Venue value) {
    try {
      return data.saveAndFlush(VenueJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByVenueCode(String value) {
    return data.existsByVenueCode(value);
  }

  @Override
  public boolean existsByVenueCodeAndIdNot(String value, long id) {
    return data.existsByVenueCodeAndIdNot(value, id);
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
