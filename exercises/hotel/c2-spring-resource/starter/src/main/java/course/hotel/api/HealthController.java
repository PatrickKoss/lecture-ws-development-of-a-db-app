package course.hotel.api;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @GetMapping("/api/health")
  Map<String, String> health() {
    return Map.of("status", "UP", "domain", "hotel");
  }
}
