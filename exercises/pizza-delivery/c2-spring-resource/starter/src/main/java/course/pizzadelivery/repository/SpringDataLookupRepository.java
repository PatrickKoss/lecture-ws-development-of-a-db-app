package course.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataLookupRepository extends JpaRepository<LookupJpaEntity, Long> {}
