package course.pizzadelivery.service;

import course.pizzadelivery.domain.Pizza;
import course.pizzadelivery.repository.PizzaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
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
        .orElseThrow(
            () -> new ResourceNotFoundException("PIZZA_NOT_FOUND", "Pizza nicht gefunden"));
  }

  @Transactional
  public Pizza create(PizzaCommand command) {
    if (repository.existsByPizzaNumber(command.pizzaNumber())) {
      throw duplicate();
    }
    return repository.save(
        new Pizza(
            null,
            command.pizzaNumber(),
            command.name(),
            command.category(),
            command.ovenStation(),
            command.basePrice(),
            command.active()));
  }

  @Transactional
  public Pizza replace(long id, PizzaCommand command) {
    findById(id);
    if (repository.existsByPizzaNumberAndIdNot(command.pizzaNumber(), id)) {
      throw duplicate();
    }
    return repository.save(
        new Pizza(
            id,
            command.pizzaNumber(),
            command.name(),
            command.category(),
            command.ovenStation(),
            command.basePrice(),
            command.active()));
  }

  @Transactional
  public void delete(long id) {
    repository.deleteById(id);
  }

  private ResourceConflictException duplicate() {
    return new ResourceConflictException(
        "PIZZA_NUMBER_EXISTS", "pizza_number ist bereits vergeben");
  }
}
