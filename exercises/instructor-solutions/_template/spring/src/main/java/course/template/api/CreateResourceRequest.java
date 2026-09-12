package course.template.api;

import course.template.service.ResourceCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Ressourcen")
public record CreateResourceRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "R-99")
        @NotBlank
        @Size(min = 1, max = 40)
        String resourceCode,
    @Schema(description = "Bezeichnung", example = "Neue Ressource")
        @NotBlank
        @Size(min = 1, max = 120)
        String name,
    @Schema(description = "Nicht negative Messgröße", example = "12.5") @NotNull @PositiveOrZero
        BigDecimal measure) {
  public ResourceCommand toCommand() {
    return new ResourceCommand(resourceCode, name, measure);
  }
}
