package course.gym.api;

import course.gym.service.CourseService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
  private final CourseService service;

  public CourseController(CourseService service) {
    this.service = service;
  }

  @GetMapping
  public List<CourseResponse> findAll() {
    return service.findAll().stream().map(CourseResponse::from).toList();
  }

  @GetMapping("/{id}")
  public CourseResponse findById(@PathVariable long id) {
    return CourseResponse.from(service.findById(id));
  }
}
