package course.museum.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "galleries")
class LookupJpaEntity {
  @Id private Long id;

  @Column(name = "name", nullable = false)
  private String label;

  protected LookupJpaEntity() {}

  String label() {
    return label;
  }
}
