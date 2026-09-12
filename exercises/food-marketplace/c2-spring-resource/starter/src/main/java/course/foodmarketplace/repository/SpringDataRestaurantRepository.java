package course.foodmarketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataRestaurantRepository extends JpaRepository<RestaurantJpaEntity, Long> {
  boolean existsByPartnerNumber(String value);

  boolean existsByPartnerNumberAndIdNot(String value, long id);
}
