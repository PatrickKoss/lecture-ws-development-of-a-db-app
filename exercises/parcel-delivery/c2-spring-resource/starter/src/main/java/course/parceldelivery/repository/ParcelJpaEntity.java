package course.parceldelivery.repository;

import course.parceldelivery.domain.Parcel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "parcels")
class ParcelJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "tracking_code", nullable = false)
  private String trackingCode;

  @Column(name = "recipient", nullable = false)
  private String recipient;

  @Column(name = "weight", nullable = false)
  private BigDecimal weight;

  protected ParcelJpaEntity() {}

  private ParcelJpaEntity(Long id, String trackingCode, String recipient, BigDecimal weight) {
    this.id = id;
    this.trackingCode = trackingCode;
    this.recipient = recipient;
    this.weight = weight;
  }

  static ParcelJpaEntity fromDomain(Parcel value) {
    return new ParcelJpaEntity(value.id(), value.trackingCode(), value.recipient(), value.weight());
  }

  Parcel toDomain() {
    return new Parcel(id, trackingCode, recipient, weight);
  }

  public Long id() {
    return id;
  }

  public String trackingCode() {
    return trackingCode;
  }

  public String recipient() {
    return recipient;
  }

  public BigDecimal weight() {
    return weight;
  }
}
