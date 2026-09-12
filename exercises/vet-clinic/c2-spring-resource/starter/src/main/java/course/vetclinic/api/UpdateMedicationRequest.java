package course.vetclinic.api;

import course.vetclinic.service.MedicationCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Eingabe für Medikamente")
public record UpdateMedicationRequest(
    @Schema(description = "Pharmazentralnummer mit acht Ziffern", example = "00999999")
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String pzn,
    @Schema(description = "Produktname", example = "Testpräparat")
        @NotBlank
        @Size(min = 1, max = 120)
        String productName,
    @Schema(description = "Wirkstoff", example = "Teststoff") @NotBlank @Size(min = 1, max = 160)
        String activeIngredient,
    @Schema(description = "Darreichungsform", example = "Tablette")
        @NotBlank
        @Size(min = 1, max = 50)
        String dosageForm,
    @Schema(description = "Gibt die Verschreibungspflicht an", example = "true") @NotNull
        Boolean prescriptionRequired,
    @Schema(description = "Gibt an, ob das Medikament geführt wird", example = "true") @NotNull
        Boolean active) {
  public MedicationCommand toCommand() {
    return new MedicationCommand(
        pzn, productName, activeIngredient, dosageForm, prescriptionRequired, active);
  }
}
