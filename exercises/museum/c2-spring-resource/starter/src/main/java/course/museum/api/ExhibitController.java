package course.museum.api;

import course.museum.service.ExhibitService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exhibits")
public class ExhibitController {
  private final ExhibitService service;

  public ExhibitController(ExhibitService service) {
    this.service = service;
  }

  @GetMapping
  public List<ExhibitResponse> findAll() {
    return service.findAll().stream().map(ExhibitResponse::from).toList();
  }

  @GetMapping("/{id}")
  public ExhibitResponse findById(@PathVariable long id) {
    return ExhibitResponse.from(service.findById(id));
  }
}
