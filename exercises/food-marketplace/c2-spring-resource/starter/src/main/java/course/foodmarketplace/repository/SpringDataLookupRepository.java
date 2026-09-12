package course.foodmarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataLookupRepository extends JpaRepository<LookupJpaEntity, Long> {}
