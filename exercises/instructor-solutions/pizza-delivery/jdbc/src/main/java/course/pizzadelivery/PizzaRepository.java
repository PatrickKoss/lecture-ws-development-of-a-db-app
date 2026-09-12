package course.pizzadelivery;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface PizzaRepository {
  Optional<Pizza> findById(long id) throws SQLException;

  List<Pizza> findAll() throws SQLException;

  Pizza insert(Pizza value) throws SQLException;
}
