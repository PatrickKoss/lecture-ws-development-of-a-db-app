package course.museum.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Exhibit")
public record CreateExhibitRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "EX-99")
    @NotBlank String inventoryCode,
    @NotBlank String title,
    @NotNull @PositiveOrZero BigDecimal insuredValue) {}
