package course.bikerental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataStationRepository extends JpaRepository<StationJpaEntity, Long> {
  boolean existsByStationCode(String value);

  boolean existsByStationCodeAndIdNot(String value, long id);
}
