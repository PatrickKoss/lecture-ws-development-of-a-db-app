package course.pizzadelivery;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository {
  Optional<Pizza> findById(long id);

  List<Pizza> findAll();

  Pizza insert(Pizza value);
}
