package course.vetclinic.api;

import course.vetclinic.service.MedicationService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medications")
public class MedicationController {
  private final MedicationService service;

  public MedicationController(MedicationService service) {
    this.service = service;
  }

  @GetMapping
  public List<MedicationResponse> findAll() {
    return service.findAll().stream().map(MedicationResponse::from).toList();
  }

  @GetMapping("/{id}")
  public MedicationResponse findById(@PathVariable long id) {
    return MedicationResponse.from(service.findById(id));
  }
}
