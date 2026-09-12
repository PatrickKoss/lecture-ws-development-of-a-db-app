package course.template.api;

import course.template.domain.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Ressourcen")
public record ResourceResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutiger fachlicher Schlüssel",
            example = "R-99",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String resourceCode,
    @Schema(
            description = "Bezeichnung",
            example = "Neue Ressource",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Nicht negative Messgröße",
            example = "12.5",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal measure) {
  public static ResourceResponse from(Resource value) {
    return new ResourceResponse(value.id(), value.resourceCode(), value.name(), value.measure());
  }
}
