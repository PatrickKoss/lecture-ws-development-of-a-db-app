package course.foodmarketplace.api;

import course.foodmarketplace.service.RestaurantCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Schema(description = "Eingabe für Restaurants")
public record UpdateRestaurantRequest(
    @Schema(description = "Eindeutige Partnernummer", example = "R-999")
        @NotBlank
        @Size(min = 1, max = 40)
        String partnerNumber,
    @Schema(description = "Restaurantname", example = "Testküche")
        @NotBlank
        @Size(min = 1, max = 100)
        String name,
    @Schema(description = "Straße und Hausnummer", example = "Markt 1")
        @NotBlank
        @Size(min = 1, max = 100)
        String street,
    @Schema(description = "Postleitzahl", example = "45127") @NotBlank @Pattern(regexp = "\\d{5}")
        String postalCode,
    @Schema(description = "Ort", example = "Essen") @NotBlank @Size(min = 1, max = 60) String city,
    @Schema(description = "Provision in Prozent", example = "12.5")
        @NotNull
        @PositiveOrZero
        @DecimalMax("100")
        BigDecimal commissionRate,
    @Schema(description = "Gibt an, ob Bestellungen möglich sind", example = "true") @NotNull
        Boolean active) {
  public RestaurantCommand toCommand() {
    return new RestaurantCommand(
        partnerNumber, name, street, postalCode, city, commissionRate, active);
  }
}
