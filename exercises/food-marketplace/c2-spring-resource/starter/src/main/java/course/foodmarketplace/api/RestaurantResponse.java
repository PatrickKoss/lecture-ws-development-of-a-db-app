package course.foodmarketplace.api;

import course.foodmarketplace.domain.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung von Restaurants")
public record RestaurantResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Eindeutige Partnernummer",
            example = "R-999",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String partnerNumber,
    @Schema(
            description = "Restaurantname",
            example = "Testküche",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String name,
    @Schema(
            description = "Straße und Hausnummer",
            example = "Markt 1",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String street,
    @Schema(
            description = "Postleitzahl",
            example = "45127",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String postalCode,
    @Schema(description = "Ort", example = "Essen", requiredMode = Schema.RequiredMode.REQUIRED)
        String city,
    @Schema(
            description = "Provision in Prozent",
            example = "12.5",
            requiredMode = Schema.RequiredMode.REQUIRED)
        BigDecimal commissionRate,
    @Schema(
            description = "Gibt an, ob Bestellungen möglich sind",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean active) {
  public static RestaurantResponse from(Restaurant value) {
    return new RestaurantResponse(
        value.id(),
        value.partnerNumber(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.commissionRate(),
        value.active());
  }
}
