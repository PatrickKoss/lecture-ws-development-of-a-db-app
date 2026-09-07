package course.vetclinic.api;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Eingabe zum Anlegen einer Ressource Medication")
public record CreateMedicationRequest(
    @Schema(description = "Eindeutiger fachlicher Schlüssel", example = "00999999")
    @NotBlank String pzn,
    @NotBlank String productName,
    @NotBlank String activeIngredient,
    @NotBlank String dosageForm,
    @NotNull Boolean prescriptionRequired,
    @NotNull Boolean active) {}
