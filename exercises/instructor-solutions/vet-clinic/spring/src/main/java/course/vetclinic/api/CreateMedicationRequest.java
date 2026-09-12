package course.vetclinic.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Eingabedaten für die Ressource Medication")
public record CreateMedicationRequest(
    @Schema(description = "Eindeutige Pharmazentralnummer", example = "00999999")
    @NotBlank String pzn,
    @Schema(description = "Produktname", example = "Testmedikament")
    @NotBlank String productName,
    @Schema(description = "Wirkstoff", example = "Wirkstoff X")
    @NotBlank String activeIngredient,
    @Schema(description = "Darreichungsform", example = "Tablette")
    @NotBlank String dosageForm,
    @Schema(description = "Kennzeichnet die Rezeptpflicht", example = "true")
    @NotNull Boolean prescriptionRequired,
    @Schema(description = "Kennzeichnet ein verfügbares Medikament", example = "true")
    @NotNull Boolean active
) {}
