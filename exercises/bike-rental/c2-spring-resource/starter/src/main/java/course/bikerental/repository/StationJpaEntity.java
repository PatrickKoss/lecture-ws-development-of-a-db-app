package course.bikerental.repository;

import course.bikerental.domain.Station;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stations")
class StationJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "station_code", nullable = false)
  private String stationCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "capacity", nullable = false)
  private Integer capacity;

  @Column(name = "status", nullable = false)
  private String status;

  protected StationJpaEntity() {}

  private StationJpaEntity(
      Long id, String stationCode, String name, String address, Integer capacity, String status) {
    this.id = id;
    this.stationCode = stationCode;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.status = status;
  }

  static StationJpaEntity fromDomain(Station value) {
    return new StationJpaEntity(
        value.id(),
        value.stationCode(),
        value.name(),
        value.address(),
        value.capacity(),
        value.status());
  }

  Station toDomain() {
    return new Station(id, stationCode, name, address, capacity, status);
  }

  public Long id() {
    return id;
  }

  public String stationCode() {
    return stationCode;
  }

  public String name() {
    return name;
  }

  public String address() {
    return address;
  }

  public Integer capacity() {
    return capacity;
  }

  public String status() {
    return status;
  }
}
