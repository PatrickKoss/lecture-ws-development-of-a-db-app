package course.carworkshop.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Schema(description = "Eingabe zum Anlegen einer Ressource Part")
public record CreatePartRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "P-1999")
    @NotBlank String partNumber,
    @NotBlank String name,
    @NotBlank String category,
    @NotBlank String shelfCode,
    @NotNull @PositiveOrZero Integer stockQuantity,
    @NotNull @PositiveOrZero Integer reorderLevel,
    @NotNull @PositiveOrZero BigDecimal listPrice) {}
