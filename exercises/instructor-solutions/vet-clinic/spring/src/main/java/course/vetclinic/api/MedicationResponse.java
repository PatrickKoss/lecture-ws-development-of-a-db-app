package course.vetclinic.api;

import io.swagger.v3.oas.annotations.media.Schema;

import course.vetclinic.domain.Medication;

@Schema(description = "API-Darstellung der Ressource Medication")
public record MedicationResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
        Long id,
    @Schema(description = "Eindeutige Pharmazentralnummer", example = "00999999") String pzn,
    @Schema(description = "Produktname", example = "Testmedikament") String productName,
    @Schema(description = "Wirkstoff", example = "Wirkstoff X") String activeIngredient,
    @Schema(description = "Darreichungsform", example = "Tablette") String dosageForm,
    @Schema(description = "Kennzeichnet die Rezeptpflicht", example = "true") Boolean prescriptionRequired,
    @Schema(description = "Kennzeichnet ein verfügbares Medikament", example = "true") Boolean active) {
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
