package course.foodmarketplace.repository;

import course.foodmarketplace.domain.Restaurant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
class RestaurantJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "partner_number", nullable = false)
  private String partnerNumber;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "postal_code", nullable = false)
  private String postalCode;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "commission_rate", nullable = false)
  private BigDecimal commissionRate;

  @Column(name = "active", nullable = false)
  private Boolean active;

  protected RestaurantJpaEntity() {}

  private RestaurantJpaEntity(
      Long id,
      String partnerNumber,
      String name,
      String street,
      String postalCode,
      String city,
      BigDecimal commissionRate,
      Boolean active) {
    this.id = id;
    this.partnerNumber = partnerNumber;
    this.name = name;
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.commissionRate = commissionRate;
    this.active = active;
  }

  static RestaurantJpaEntity fromDomain(Restaurant value) {
    return new RestaurantJpaEntity(
        value.id(),
        value.partnerNumber(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.commissionRate(),
        value.active());
  }

  Restaurant toDomain() {
    return new Restaurant(
        id, partnerNumber, name, street, postalCode, city, commissionRate, active);
  }

  public Long id() {
    return id;
  }

  public String partnerNumber() {
    return partnerNumber;
  }

  public String name() {
    return name;
  }

  public String street() {
    return street;
  }

  public String postalCode() {
    return postalCode;
  }

  public String city() {
    return city;
  }

  public BigDecimal commissionRate() {
    return commissionRate;
  }

  public Boolean active() {
    return active;
  }
}
