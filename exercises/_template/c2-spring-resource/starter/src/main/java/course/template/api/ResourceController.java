package course.template.api;

import course.template.service.ResourceService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
  private final ResourceService service;

  public ResourceController(ResourceService service) {
    this.service = service;
  }

  @GetMapping
  public List<ResourceResponse> findAll() {
    return service.findAll().stream().map(ResourceResponse::from).toList();
  }

  @GetMapping("/{id}")
  public ResourceResponse findById(@PathVariable long id) {
    return ResourceResponse.from(service.findById(id));
  }
}
