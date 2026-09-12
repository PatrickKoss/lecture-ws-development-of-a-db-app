package course.vetclinic.service;

public record MedicationCommand(
    String pzn,
    String productName,
    String activeIngredient,
    String dosageForm,
    Boolean prescriptionRequired,
    Boolean active) {}
