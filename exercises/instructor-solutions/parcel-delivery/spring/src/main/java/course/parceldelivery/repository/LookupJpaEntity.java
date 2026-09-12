package course.parceldelivery.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "depots")
class LookupJpaEntity {
  @Id private Long id;

  @Column(name = "city", nullable = false)
  private String label;

  protected LookupJpaEntity() {}

  String label() {
    return label;
  }
}
