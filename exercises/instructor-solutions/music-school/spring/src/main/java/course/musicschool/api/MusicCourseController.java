package course.musicschool.api;

import course.musicschool.service.MusicCourseService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class MusicCourseController {
  private final MusicCourseService service;

  public MusicCourseController(MusicCourseService service) {
    this.service = service;
  }

  @GetMapping
  public List<MusicCourseResponse> findAll() {
    return service.findAll().stream().map(MusicCourseResponse::from).toList();
  }

  @GetMapping("/{id}")
  public MusicCourseResponse findById(@PathVariable long id) {
    return MusicCourseResponse.from(service.findById(id));
  }

  @PostMapping
  public ResponseEntity<MusicCourseResponse> create(
      @Valid @RequestBody CreateMusicCourseRequest request) {
    var created = service.create(request);
    var location = URI.create("/api/courses/" + created.id());
    return ResponseEntity.created(location).body(MusicCourseResponse.from(created));
  }
}
