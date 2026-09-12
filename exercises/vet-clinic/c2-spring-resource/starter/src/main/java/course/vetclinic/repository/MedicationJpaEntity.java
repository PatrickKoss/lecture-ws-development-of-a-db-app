package course.vetclinic.repository;

import course.vetclinic.domain.Medication;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "medications")
class MedicationJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "pzn", nullable = false)
  private String pzn;

  @Column(name = "product_name", nullable = false)
  private String productName;

  @Column(name = "active_ingredient", nullable = false)
  private String activeIngredient;

  @Column(name = "dosage_form", nullable = false)
  private String dosageForm;

  @Column(name = "prescription_required", nullable = false)
  private Boolean prescriptionRequired;

  @Column(name = "active", nullable = false)
  private Boolean active;

  protected MedicationJpaEntity() {}

  private MedicationJpaEntity(
      Long id,
      String pzn,
      String productName,
      String activeIngredient,
      String dosageForm,
      Boolean prescriptionRequired,
      Boolean active) {
    this.id = id;
    this.pzn = pzn;
    this.productName = productName;
    this.activeIngredient = activeIngredient;
    this.dosageForm = dosageForm;
    this.prescriptionRequired = prescriptionRequired;
    this.active = active;
  }

  static MedicationJpaEntity fromDomain(Medication value) {
    return new MedicationJpaEntity(
        value.id(),
        value.pzn(),
        value.productName(),
        value.activeIngredient(),
        value.dosageForm(),
        value.prescriptionRequired(),
        value.active());
  }

  Medication toDomain() {
    return new Medication(
        id, pzn, productName, activeIngredient, dosageForm, prescriptionRequired, active);
  }

  public Long id() {
    return id;
  }

  public String pzn() {
    return pzn;
  }

  public String productName() {
    return productName;
  }

  public String activeIngredient() {
    return activeIngredient;
  }

  public String dosageForm() {
    return dosageForm;
  }

  public Boolean prescriptionRequired() {
    return prescriptionRequired;
  }

  public Boolean active() {
    return active;
  }
}
