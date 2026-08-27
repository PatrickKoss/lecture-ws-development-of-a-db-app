package course.vetclinic.api;

import course.vetclinic.domain.Medication;

public record MedicationResponse(
    Long id,
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
