package course.museum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataExhibitRepository extends JpaRepository<ExhibitJpaEntity, Long> {
  boolean existsByInventoryCode(String value);

  boolean existsByInventoryCodeAndIdNot(String value, long id);
}
