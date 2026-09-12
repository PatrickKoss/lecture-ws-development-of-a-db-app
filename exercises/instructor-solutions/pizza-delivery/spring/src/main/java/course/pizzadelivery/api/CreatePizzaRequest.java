package course.pizzadelivery.api;

import course.pizzadelivery.service.PizzaCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Pizzen")
public record CreatePizzaRequest(
    @Schema(description = "Eindeutige Pizzanummer", example = "P-99")
        @NotBlank
        @Pattern(regexp = "P-\\d{2}")
        String pizzaNumber,
    @Schema(description = "Name der Pizza", example = "Testpizza")
        @NotBlank
        @Size(min = 1, max = 100)
        String name,
    @Schema(description = "Kategorie der Speisekarte", example = "Saisonal")
        @NotBlank
        @Size(min = 1, max = 30)
        String category,
    @Schema(description = "Zuständige Ofenstation", example = "OFEN-Z")
        @NotBlank
        @Size(min = 1, max = 20)
        String ovenStation,
    @Schema(description = "Grundpreis", example = "11.50") @NotNull @Positive BigDecimal basePrice,
    @Schema(description = "Gibt an, ob die Pizza bestellbar ist", example = "true") @NotNull
        Boolean active) {
  public PizzaCommand toCommand() {
    return new PizzaCommand(pizzaNumber, name, category, ovenStation, basePrice, active);
  }
}
