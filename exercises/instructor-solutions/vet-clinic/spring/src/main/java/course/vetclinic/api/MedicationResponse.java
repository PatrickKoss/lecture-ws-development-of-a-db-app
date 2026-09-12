package course.vetclinic.api;

import course.vetclinic.domain.Medication;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Öffentliche Darstellung von Medikamente")
public record MedicationResponse(
    @Schema(
            description = "Vom Server vergebene ID",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY,
            requiredMode = Schema.RequiredMode.REQUIRED)
        Long id,
    @Schema(
            description = "Pharmazentralnummer mit acht Ziffern",
            example = "00999999",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String pzn,
    @Schema(
            description = "Produktname",
            example = "Testpräparat",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String productName,
    @Schema(
            description = "Wirkstoff",
            example = "Teststoff",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String activeIngredient,
    @Schema(
            description = "Darreichungsform",
            example = "Tablette",
            requiredMode = Schema.RequiredMode.REQUIRED)
        String dosageForm,
    @Schema(
            description = "Gibt die Verschreibungspflicht an",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
        Boolean prescriptionRequired,
    @Schema(
            description = "Gibt an, ob das Medikament geführt wird",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED)
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
