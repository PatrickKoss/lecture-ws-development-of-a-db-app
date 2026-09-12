package course.museum.repository;

import course.museum.domain.Exhibit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "exhibits")
class ExhibitJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "inventory_code", nullable = false)
  private String inventoryCode;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "insured_value", nullable = false)
  private BigDecimal insuredValue;

  protected ExhibitJpaEntity() {}

  private ExhibitJpaEntity(Long id, String inventoryCode, String title, BigDecimal insuredValue) {
    this.id = id;
    this.inventoryCode = inventoryCode;
    this.title = title;
    this.insuredValue = insuredValue;
  }

  static ExhibitJpaEntity fromDomain(Exhibit value) {
    return new ExhibitJpaEntity(
        value.id(), value.inventoryCode(), value.title(), value.insuredValue());
  }

  Exhibit toDomain() {
    return new Exhibit(id, inventoryCode, title, insuredValue);
  }

  public Long id() {
    return id;
  }

  public String inventoryCode() {
    return inventoryCode;
  }

  public String title() {
    return title;
  }

  public BigDecimal insuredValue() {
    return insuredValue;
  }
}
