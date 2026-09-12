package course.template.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataResourceRepository extends JpaRepository<ResourceJpaEntity, Long> {
  boolean existsByResourceCode(String value);

  boolean existsByResourceCodeAndIdNot(String value, long id);
}
