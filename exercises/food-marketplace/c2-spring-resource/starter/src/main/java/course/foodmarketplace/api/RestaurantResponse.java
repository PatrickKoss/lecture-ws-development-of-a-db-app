package course.foodmarketplace.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.foodmarketplace.domain.Restaurant;
import java.math.BigDecimal;

@Schema(description = "Öffentliche Darstellung der Ressource Restaurant")
public record RestaurantResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String partnerNumber,
    String name,
    String street,
    String postalCode,
    String city,
    BigDecimal commissionRate,
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
