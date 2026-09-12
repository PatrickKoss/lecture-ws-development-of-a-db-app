package course.eventtickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataLookupRepository extends JpaRepository<LookupJpaEntity, Long> {}
