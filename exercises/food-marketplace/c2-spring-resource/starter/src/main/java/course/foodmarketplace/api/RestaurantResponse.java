package course.foodmarketplace.api;

import course.foodmarketplace.domain.Restaurant;
import java.math.BigDecimal;

public record RestaurantResponse(
    Long id,
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
