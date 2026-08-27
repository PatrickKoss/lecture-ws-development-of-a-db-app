package course.template.api;

import course.template.domain.Resource;
import java.math.BigDecimal;

public record ResourceResponse(Long id, String resourceCode, String name, BigDecimal measure) {
  public static ResourceResponse from(Resource value) {
    return new ResourceResponse(value.id(), value.resourceCode(), value.name(), value.measure());
  }
}
