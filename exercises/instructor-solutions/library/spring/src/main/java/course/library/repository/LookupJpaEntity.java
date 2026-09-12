package course.library.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
class LookupJpaEntity {
  @Id private Long id;

  @Column(name = "last_name", nullable = false)
  private String label;

  protected LookupJpaEntity() {}

  String label() {
    return label;
  }
}
