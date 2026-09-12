package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {
  private final SpringDataRestaurantRepository data;

  public JpaRestaurantRepository(SpringDataRestaurantRepository data) {
    this.data = data;
  }

  @Override
  public List<Restaurant> findAll() {
    throw new UnsupportedOperationException("TODO C2: findAll über Spring Data implementieren");
  }

  @Override
  public Optional<Restaurant> findById(long id) {
    throw new UnsupportedOperationException("TODO C2: findById über Spring Data implementieren");
  }

  @Override
  public Restaurant save(Restaurant value) {
    throw new UnsupportedOperationException(
        "TODO C3: mit saveAndFlush speichern und Constraints übersetzen");
  }

  @Override
  public boolean existsByPartnerNumber(String value) {
    throw new UnsupportedOperationException("TODO C3: abgeleitete Exists-Methode verwenden");
  }

  @Override
  public boolean existsByPartnerNumberAndIdNot(String value, long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: Konfliktprüfung für PUT implementieren");
  }

  @Override
  public void deleteById(long id) {
    throw new UnsupportedOperationException(
        "TODO Vertiefung: idempotentes DELETE mit flush implementieren");
  }
}
