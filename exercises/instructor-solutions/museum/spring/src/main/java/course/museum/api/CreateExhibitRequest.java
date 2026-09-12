package course.museum.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabedaten für die Ressource Exhibit")
public record CreateExhibitRequest(
    @Schema(description = "Eindeutige Inventarnummer", example = "EX-99")
    @NotBlank String inventoryCode,
    @Schema(description = "Titel des Exponats", example = "Stadtansicht")
    @NotBlank String title,
    @Schema(description = "Versicherungswert in Euro", example = "25000.00")
    @NotNull @PositiveOrZero BigDecimal insuredValue
) {}
