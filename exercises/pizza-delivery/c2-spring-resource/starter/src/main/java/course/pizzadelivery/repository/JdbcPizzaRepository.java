package course.pizzadelivery.repository;

import course.pizzadelivery.domain.Pizza;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcPizzaRepository implements PizzaRepository {
  private final JdbcTemplate jdbc;

  public JdbcPizzaRepository(JdbcTemplate jdbc) {
    this.jdbc = jdbc;
  }

  public List<Pizza> findAll() {
    throw new UnsupportedOperationException("TODO C2 findAll");
  }

  public Optional<Pizza> findById(long id) {
    throw new UnsupportedOperationException("TODO C2 findById");
  }

  public Pizza insert(Pizza value) {
    throw new UnsupportedOperationException("TODO C2 insert");
  }

  public boolean existsByPizzaNumber(String pizzaNumber) {
    throw new UnsupportedOperationException("TODO C3 conflict");
  }
}
