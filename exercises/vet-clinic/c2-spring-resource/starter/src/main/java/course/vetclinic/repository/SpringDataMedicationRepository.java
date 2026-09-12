package course.vetclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataMedicationRepository extends JpaRepository<MedicationJpaEntity, Long> {
  boolean existsByPzn(String value);

  boolean existsByPznAndIdNot(String value, long id);
}
