package course.foodmarketplace.api;

import course.foodmarketplace.service.RestaurantCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Restaurants")
public record CreateRestaurantRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public RestaurantCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
