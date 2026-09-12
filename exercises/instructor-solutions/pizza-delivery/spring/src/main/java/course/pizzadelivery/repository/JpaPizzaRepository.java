package course.pizzadelivery.repository;

import course.pizzadelivery.domain.Pizza;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaPizzaRepository implements PizzaRepository {
  private final SpringDataPizzaRepository data;

  public JpaPizzaRepository(SpringDataPizzaRepository data) {
    this.data = data;
  }

  @Override
  public List<Pizza> findAll() {
    return data.findAll(Sort.by("id")).stream().map(PizzaJpaEntity::toDomain).toList();
  }

  @Override
  public Optional<Pizza> findById(long id) {
    return data.findById(id).map(PizzaJpaEntity::toDomain);
  }

  @Override
  public Pizza save(Pizza value) {
    try {
      return data.saveAndFlush(PizzaJpaEntity.fromDomain(value)).toDomain();
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }

  @Override
  public boolean existsByPizzaNumber(String value) {
    return data.existsByPizzaNumber(value);
  }

  @Override
  public boolean existsByPizzaNumberAndIdNot(String value, long id) {
    return data.existsByPizzaNumberAndIdNot(value, id);
  }

  @Override
  public void deleteById(long id) {
    try {
      data.findById(id)
          .ifPresent(
              found -> {
                data.delete(found);
                data.flush();
              });
    } catch (RuntimeException error) {
      throw SQLiteConstraintTranslator.translate(error);
    }
  }
}
