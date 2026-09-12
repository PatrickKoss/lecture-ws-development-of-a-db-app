package course.hotel.repository;

import course.hotel.domain.RoomType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "room_types")
class RoomTypeJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "type_code", nullable = false)
  private String typeCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Column(name = "standard_price_cents", nullable = false)
  private Integer standardPriceCents;

  protected RoomTypeJpaEntity() {}

  private RoomTypeJpaEntity(
      Long id, String typeCode, String name, Integer capacity, Integer standardPriceCents) {
    this.id = id;
    this.typeCode = typeCode;
    this.name = name;
    this.capacity = capacity;
    this.standardPriceCents = standardPriceCents;
  }

  static RoomTypeJpaEntity fromDomain(RoomType value) {
    return new RoomTypeJpaEntity(
        value.id(), value.typeCode(), value.name(), value.capacity(), value.standardPriceCents());
  }

  RoomType toDomain() {
    return new RoomType(id, typeCode, name, capacity, standardPriceCents);
  }

  public Long id() {
    return id;
  }

  public String typeCode() {
    return typeCode;
  }

  public String name() {
    return name;
  }

  public Integer capacity() {
    return capacity;
  }

  public Integer standardPriceCents() {
    return standardPriceCents;
  }
}
