package course.parceldelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataParcelRepository extends JpaRepository<ParcelJpaEntity, Long> {
  boolean existsByTrackingCode(String value);

  boolean existsByTrackingCodeAndIdNot(String value, long id);
}
