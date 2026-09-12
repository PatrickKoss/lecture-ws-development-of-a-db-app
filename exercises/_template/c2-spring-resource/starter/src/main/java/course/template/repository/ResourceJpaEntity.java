package course.template.repository;

import course.template.domain.Resource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "resources")
class ResourceJpaEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "resource_code", nullable = false)
  private String resourceCode;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "measure", nullable = false)
  private BigDecimal measure;

  protected ResourceJpaEntity() {}

  private ResourceJpaEntity(Long id, String resourceCode, String name, BigDecimal measure) {
    this.id = id;
    this.resourceCode = resourceCode;
    this.name = name;
    this.measure = measure;
  }

  static ResourceJpaEntity fromDomain(Resource value) {
    return new ResourceJpaEntity(value.id(), value.resourceCode(), value.name(), value.measure());
  }

  Resource toDomain() {
    return new Resource(id, resourceCode, name, measure);
  }

  public Long id() {
    return id;
  }

  public String resourceCode() {
    return resourceCode;
  }

  public String name() {
    return name;
  }

  public BigDecimal measure() {
    return measure;
  }
}
