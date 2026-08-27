package course.bikerental.api;

import course.bikerental.service.StationService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
public class StationController {
  private final StationService service;

  public StationController(StationService service) {
    this.service = service;
  }

  @GetMapping
  public List<StationResponse> findAll() {
    return service.findAll().stream().map(StationResponse::from).toList();
  }

  @GetMapping("/{id}")
  public StationResponse findById(@PathVariable long id) {
    return StationResponse.from(service.findById(id));
  }
}
