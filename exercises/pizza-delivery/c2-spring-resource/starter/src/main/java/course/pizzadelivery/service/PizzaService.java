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
    throw new UnsupportedOperationException("TODO C2: Alle Datensätze aus dem Repository lesen");
  }

  public Pizza findById(long id) {
    throw new UnsupportedOperationException("TODO C2: Datensatz lesen und unbekannte ID als 404 melden");
  }

  @Transactional
  public Pizza create(PizzaCommand command) {
    throw new UnsupportedOperationException("TODO C3: Fachschlüssel prüfen und Datensatz speichern");
  }

}
