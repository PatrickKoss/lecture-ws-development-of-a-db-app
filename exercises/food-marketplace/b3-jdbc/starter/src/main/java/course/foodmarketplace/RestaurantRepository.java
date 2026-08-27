package course.foodmarketplace;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {
  Optional<Restaurant> findById(long id) throws SQLException;

  List<Restaurant> findAll() throws SQLException;
}
