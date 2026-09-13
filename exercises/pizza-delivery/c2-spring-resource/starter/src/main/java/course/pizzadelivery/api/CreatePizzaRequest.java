package course.pizzadelivery.api;

import course.pizzadelivery.service.PizzaCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Pizzen")
public record CreatePizzaRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public PizzaCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
