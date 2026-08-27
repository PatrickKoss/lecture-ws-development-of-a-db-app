package course.cinema.api;

import course.cinema.service.MovieService;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
  private final MovieService service;

  public MovieController(MovieService service) {
    this.service = service;
  }

  @GetMapping
  public List<MovieResponse> findAll() {
    return service.findAll().stream().map(MovieResponse::from).toList();
  }

  @GetMapping("/{id}")
  public MovieResponse findById(@PathVariable long id) {
    return MovieResponse.from(service.findById(id));
  }
}
