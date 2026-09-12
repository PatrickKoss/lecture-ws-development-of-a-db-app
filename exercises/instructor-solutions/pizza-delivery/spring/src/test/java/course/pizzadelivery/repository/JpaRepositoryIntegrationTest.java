package course.pizzadelivery.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.pizzadelivery.domain.Pizza;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired PizzaRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Mozzarella",
            "Salami",
            "Champignons",
            "Peperoni",
            "Kochschinken",
            "Ananas",
            "Thunfisch",
            "Zwiebeln",
            "Paprika",
            "Spinat",
            "Gorgonzola",
            "Meeresfrüchte");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate =
        new Pizza(null, "P-01", "Testpizza", "Saisonal", "OFEN-Z", new BigDecimal("11.5"), true);
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
