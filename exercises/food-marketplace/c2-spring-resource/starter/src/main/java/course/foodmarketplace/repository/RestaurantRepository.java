package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
  List<Restaurant> findAll();

  Optional<Restaurant> findById(long id);

  Restaurant insert(Restaurant value);

  boolean existsByPartnerNumber(String partnerNumber);
}
