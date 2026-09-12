package course.parceldelivery.api;

import course.parceldelivery.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
  @Operation(summary = "Anwendungsstatus lesen")
  @ApiResponse(responseCode = "200", description = "Anwendung ist erreichbar")
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/api/health")
  Map<String, String> health() {
    return Map.of("status", "UP", "domain", "parcel-delivery");
  }
}
