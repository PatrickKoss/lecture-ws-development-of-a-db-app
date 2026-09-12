package course.eventtickets.repository;

import course.eventtickets.domain.Venue;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaVenueRepository implements VenueRepository {
  private final SpringDataVenueRepository data;

  public JpaVenueRepository(SpringDataVenueRepository data) {
    this.data = data;
  }

  @Override
  public List<Venue> findAll() {
    throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
  }

  @Override
  public Optional<Venue> findById(long id) {
    throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
  }

  @Override
  public Venue save(Venue value) {
    throw new UnsupportedOperationException(
        "TODO C3: mit saveAndFlush speichern und Constraints übersetzen");
  }

  @Override
  public boolean existsByVenueCode(String value) {
    throw new UnsupportedOperationException("TODO C3: abgeleitete Exists-Methode verwenden");
  }

  @Override
  public boolean existsByVenueCodeAndIdNot(String value, long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: Konfliktprüfung für PUT implementieren");
  }

  @Override
  public void deleteById(long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: idempotentes DELETE mit flush implementieren");
  }
}
