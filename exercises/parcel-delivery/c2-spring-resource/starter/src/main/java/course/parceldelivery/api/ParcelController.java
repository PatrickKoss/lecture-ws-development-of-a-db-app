package course.parceldelivery.api;

import course.parceldelivery.service.ParcelService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
  private final ParcelService service;

  public ParcelController(ParcelService service) {
    this.service = service;
  }

  @GetMapping
  public List<ParcelResponse> findAll() {
    return service.findAll().stream().map(ParcelResponse::from).toList();
  }

  @GetMapping("/{id}")
  public ParcelResponse findById(@PathVariable long id) {
    return ParcelResponse.from(service.findById(id));
  }
}
