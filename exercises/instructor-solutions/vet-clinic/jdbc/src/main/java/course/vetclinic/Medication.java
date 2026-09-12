package course.vetclinic;

public record Medication(
    Long id,
    String pzn,
    String productName,
    String activeIngredient,
    String dosageForm,
    Boolean prescriptionRequired,
    Boolean active) {}
