package course.vetclinic.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateMedicationRequest(
    @NotBlank String pzn,
    @NotBlank String productName,
    @NotBlank String activeIngredient,
    @NotBlank String dosageForm,
    @NotNull Boolean prescriptionRequired,
    @NotNull Boolean active) {}
