package course.eventtickets.repository;

import course.eventtickets.domain.Venue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "venues")
class VenueJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "venue_code", nullable = false)
  private String venueCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "street", nullable = false)
  private String street;

  @Column(name = "postal_code", nullable = false)
  private String postalCode;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  protected VenueJpaEntity() {}

  private VenueJpaEntity(
      Long id,
      String venueCode,
      String name,
      String street,
      String postalCode,
      String city,
      Integer capacity) {
    this.id = id;
    this.venueCode = venueCode;
    this.name = name;
    this.street = street;
    this.postalCode = postalCode;
    this.city = city;
    this.capacity = capacity;
  }

  static VenueJpaEntity fromDomain(Venue value) {
    return new VenueJpaEntity(
        value.id(),
        value.venueCode(),
        value.name(),
        value.street(),
        value.postalCode(),
        value.city(),
        value.capacity());
  }

  Venue toDomain() {
    return new Venue(id, venueCode, name, street, postalCode, city, capacity);
  }

  public Long id() {
    return id;
  }

  public String venueCode() {
    return venueCode;
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

  public Integer capacity() {
    return capacity;
  }
}
