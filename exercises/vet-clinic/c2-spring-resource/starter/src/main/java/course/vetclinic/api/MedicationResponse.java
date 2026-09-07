package course.vetclinic.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.vetclinic.domain.Medication;

@Schema(description = "Öffentliche Darstellung der Ressource Medication")
public record MedicationResponse(
    @Schema(description = "Vom Server vergebene ID", example = "1",
        accessMode = Schema.AccessMode.READ_ONLY) Long id,
    String pzn,
    String productName,
    String activeIngredient,
    String dosageForm,
    Boolean prescriptionRequired,
    Boolean active) {
  public static MedicationResponse from(Medication value) {
    return new MedicationResponse(
        value.id(),
        value.pzn(),
        value.productName(),
        value.activeIngredient(),
        value.dosageForm(),
        value.prescriptionRequired(),
        value.active());
  }
}
