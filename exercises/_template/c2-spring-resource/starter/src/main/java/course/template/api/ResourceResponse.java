package course.template.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.template.domain.Resource;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Resource")
public record ResourceResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id, String resourceCode, String name, BigDecimal measure) {
  public static ResourceResponse from(Resource value) {
    return new ResourceResponse(value.id(), value.resourceCode(), value.name(), value.measure());
  }
}
