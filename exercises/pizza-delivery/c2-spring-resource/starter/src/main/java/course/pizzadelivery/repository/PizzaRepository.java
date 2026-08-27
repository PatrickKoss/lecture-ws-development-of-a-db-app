package course.pizzadelivery.repository;

import course.pizzadelivery.domain.Pizza;
import java.util.List;
import java.util.Optional;

public interface PizzaRepository {
  List<Pizza> findAll();

  Optional<Pizza> findById(long id);

  Pizza insert(Pizza value);

  boolean existsByPizzaNumber(String pizzaNumber);
}
