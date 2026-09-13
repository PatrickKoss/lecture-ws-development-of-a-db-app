package course.template.api;

import course.template.service.ResourceCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Ressourcen")
public record CreateResourceRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public ResourceCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
