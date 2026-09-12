package course.pizzadelivery;
import java.util.List;
public final class PizzaCatalog {
    private final PizzaRepository repository;
    public PizzaCatalog(PizzaRepository repository) { this.repository = repository; }
    public List<Pizza> list() { return repository.findAll(); }
}
