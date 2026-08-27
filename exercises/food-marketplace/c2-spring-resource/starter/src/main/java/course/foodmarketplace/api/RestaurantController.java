package course.foodmarketplace.api;

import course.foodmarketplace.service.RestaurantService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
  private final RestaurantService service;

  public RestaurantController(RestaurantService service) {
    this.service = service;
  }

  @GetMapping
  public List<RestaurantResponse> findAll() {
    return service.findAll().stream().map(RestaurantResponse::from).toList();
  }

  @GetMapping("/{id}")
  public RestaurantResponse findById(@PathVariable long id) {
    return RestaurantResponse.from(service.findById(id));
  }
}
