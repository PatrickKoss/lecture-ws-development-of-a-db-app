package course.foodmarketplace;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
  Optional<Restaurant> findById(long id);

  List<Restaurant> findAll();

  Restaurant insert(Restaurant value);
}
