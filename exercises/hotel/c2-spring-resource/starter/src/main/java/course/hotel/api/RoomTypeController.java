package course.hotel.api;

import course.hotel.service.RoomTypeService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {
  private final RoomTypeService service;

  public RoomTypeController(RoomTypeService service) {
    this.service = service;
  }

  @GetMapping
  public List<RoomTypeResponse> findAll() {
    return service.findAll().stream().map(RoomTypeResponse::from).toList();
  }

  @GetMapping("/{id}")
  public RoomTypeResponse findById(@PathVariable long id) {
    return RoomTypeResponse.from(service.findById(id));
  }
}
