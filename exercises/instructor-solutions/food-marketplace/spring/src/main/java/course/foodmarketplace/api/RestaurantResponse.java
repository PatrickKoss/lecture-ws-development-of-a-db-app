package course.foodmarketplace.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.foodmarketplace.domain.Restaurant;
import java.math.BigDecimal;

@Schema(description = "API-Darstellung der Ressource Restaurant")
public record RestaurantResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige Partnernummer", example = "R-199") String partnerNumber,
    @Schema(description = "Name des Restaurants", example = "Pasta Haus") String name,
    @Schema(description = "Straße und Hausnummer", example = "Marktstraße 8") String street,
    @Schema(description = "Postleitzahl", example = "50667") String postalCode,
    @Schema(description = "Ort", example = "Köln") String city,
    @Schema(description = "Provisionssatz in Prozent", example = "12.50") BigDecimal commissionRate,
    @Schema(description = "Kennzeichnet einen aktiven Partner", example = "true") Boolean active) {
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
