package course.carworkshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPartRepository extends JpaRepository<PartJpaEntity, Long> {
  boolean existsByPartNumber(String value);

  boolean existsByPartNumberAndIdNot(String value, long id);
}
