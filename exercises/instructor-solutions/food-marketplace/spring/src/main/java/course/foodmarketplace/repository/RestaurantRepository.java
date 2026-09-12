package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
  List<Restaurant> findAll();

  Optional<Restaurant> findById(long id);

  Restaurant save(Restaurant value);

  boolean existsByPartnerNumber(String value);

  boolean existsByPartnerNumberAndIdNot(String value, long id);

  void deleteById(long id);
}
