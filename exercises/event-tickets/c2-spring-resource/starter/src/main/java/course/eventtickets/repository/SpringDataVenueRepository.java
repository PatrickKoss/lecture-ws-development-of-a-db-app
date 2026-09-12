package course.eventtickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataVenueRepository extends JpaRepository<VenueJpaEntity, Long> {
  boolean existsByVenueCode(String value);

  boolean existsByVenueCodeAndIdNot(String value, long id);
}
