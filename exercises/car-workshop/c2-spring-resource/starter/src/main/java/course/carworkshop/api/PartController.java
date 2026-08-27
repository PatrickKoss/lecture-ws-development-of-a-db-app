package course.carworkshop.api;

import course.carworkshop.service.PartService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parts")
public class PartController {
  private final PartService service;

  public PartController(PartService service) {
    this.service = service;
  }

  @GetMapping
  public List<PartResponse> findAll() {
    return service.findAll().stream().map(PartResponse::from).toList();
  }

  @GetMapping("/{id}")
  public PartResponse findById(@PathVariable long id) {
    return PartResponse.from(service.findById(id));
  }
}
