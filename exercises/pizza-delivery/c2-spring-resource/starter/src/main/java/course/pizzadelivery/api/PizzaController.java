package course.pizzadelivery.api;

import course.pizzadelivery.service.PizzaService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
  private final PizzaService service;

  public PizzaController(PizzaService service) {
    this.service = service;
  }

  @GetMapping
  public List<PizzaResponse> findAll() {
    return service.findAll().stream().map(PizzaResponse::from).toList();
  }

  @GetMapping("/{id}")
  public PizzaResponse findById(@PathVariable long id) {
    return PizzaResponse.from(service.findById(id));
  }
}
