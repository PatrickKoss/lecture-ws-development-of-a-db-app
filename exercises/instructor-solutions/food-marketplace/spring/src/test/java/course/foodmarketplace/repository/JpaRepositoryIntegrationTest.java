package course.foodmarketplace.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.foodmarketplace.domain.Restaurant;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired RestaurantRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Kaya",
            "Krüger",
            "Schulte",
            "Becker",
            "Romano",
            "Acar",
            "Neumann",
            "Arslan",
            "Kowalski",
            "Fuchs");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate =
        new Restaurant(
            null, "R-101", "Testküche", "Markt 1", "45127", "Essen", new BigDecimal("12.5"), true);
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
