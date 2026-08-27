package course.pizzadelivery.service;

import course.pizzadelivery.api.CreatePizzaRequest;
import course.pizzadelivery.domain.Pizza;
import course.pizzadelivery.repository.PizzaRepository;
import course.pizzadelivery.web.ConflictException;
import course.pizzadelivery.web.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
  private final PizzaRepository repository;

  public PizzaService(PizzaRepository repository) {
    this.repository = repository;
  }

  public List<Pizza> findAll() {
    return repository.findAll();
  }

  public Pizza findById(long id) {
    return repository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("PIZZA_NOT_FOUND", "Pizza nicht gefunden"));
  }

  public Pizza create(CreatePizzaRequest request) {
    if (repository.existsByPizzaNumber(request.pizzaNumber()))
      throw new ConflictException(
          "PIZZA_PIZZA_NUMBER_EXISTS", "Pizza mit diesem Wert für pizza_number existiert bereits");
    return repository.insert(
        new Pizza(
            null,
            request.pizzaNumber(),
            request.name(),
            request.category(),
            request.ovenStation(),
            request.basePrice(),
            request.active()));
  }
}
