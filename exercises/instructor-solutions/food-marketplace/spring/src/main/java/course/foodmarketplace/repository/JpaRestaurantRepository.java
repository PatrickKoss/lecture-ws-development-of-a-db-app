package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaRestaurantRepository implements RestaurantRepository {
  private final SpringDataRestaurantRepository data;

  public JpaRestaurantRepository(SpringDataRestaurantRepository data) {
    this.data = data;
  }

  @Override
  public List<Restaurant> findAll() {
    return data.findAll(Sort.by("id")).stream().map(RestaurantJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Restaurant> findById(long id) {
    return data.findById(id).map(RestaurantJpaEntity::toDomain);
  }

  @Override
  public Restaurant save(Restaurant value) {
    try {
      return data.saveAndFlush(RestaurantJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByPartnerNumber(String value) {
    return data.existsByPartnerNumber(value);
  }

  @Override
  public boolean existsByPartnerNumberAndIdNot(String value, long id) {
    return data.existsByPartnerNumberAndIdNot(value, id);
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
