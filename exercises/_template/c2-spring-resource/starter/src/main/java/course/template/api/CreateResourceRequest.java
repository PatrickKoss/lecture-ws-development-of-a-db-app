package course.template.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Resource")
public record CreateResourceRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "R-99")
    @NotBlank String resourceCode,
    @NotBlank String name,
    @NotNull @PositiveOrZero BigDecimal measure) {}
