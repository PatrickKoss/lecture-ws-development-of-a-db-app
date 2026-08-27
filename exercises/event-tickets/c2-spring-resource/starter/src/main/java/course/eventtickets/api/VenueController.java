package course.eventtickets.api;

import course.eventtickets.service.VenueService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/venues")
public class VenueController {
  private final VenueService service;

  public VenueController(VenueService service) {
    this.service = service;
  }

  @GetMapping
  public List<VenueResponse> findAll() {
    return service.findAll().stream().map(VenueResponse::from).toList();
  }

  @GetMapping("/{id}")
  public VenueResponse findById(@PathVariable long id) {
    return VenueResponse.from(service.findById(id));
  }
}
