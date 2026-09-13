package course.vetclinic.api;

import course.vetclinic.service.MedicationCommand;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Eingabe für Medikamente")
public record CreateMedicationRequest() {
  // TODO C1: Ergänzt die Request-Felder und ihre OpenAPI-Beschreibungen.
  // TODO C3: Ergänzt Bean-Validation und bildet den Request auf das Command ab.
  public MedicationCommand toCommand() {
    throw new UnsupportedOperationException("TODO C3: Request in Command umwandeln");
  }
}
