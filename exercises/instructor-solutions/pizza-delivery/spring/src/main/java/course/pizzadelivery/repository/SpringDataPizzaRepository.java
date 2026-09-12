package course.pizzadelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPizzaRepository extends JpaRepository<PizzaJpaEntity, Long> {
  boolean existsByPizzaNumber(String value);

  boolean existsByPizzaNumberAndIdNot(String value, long id);
}
