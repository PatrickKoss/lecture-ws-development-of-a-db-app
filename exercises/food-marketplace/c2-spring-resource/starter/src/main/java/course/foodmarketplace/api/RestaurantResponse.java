package course.foodmarketplace.api;

import course.foodmarketplace.domain.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Restaurants")
public record RestaurantResponse() {
  // TODO C1: Ergänzt die Response-Felder aus der Feldspezifikation im Aufgabenblatt.
  public static RestaurantResponse from(Restaurant value) {
    throw new UnsupportedOperationException("TODO C2: Domainobjekt in Response umwandeln");
  }
}
