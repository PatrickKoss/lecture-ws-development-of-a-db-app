package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcRestaurantRepository implements RestaurantRepository {
  private final JdbcTemplate jdbc;

  public JdbcRestaurantRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Restaurant> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Restaurant> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Restaurant insert(Restaurant value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByPartnerNumber(String partnerNumber) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
