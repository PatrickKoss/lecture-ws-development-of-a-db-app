package course.carworkshop.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.carworkshop.domain.Part;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired PartRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Becker", "Scholz", "Yilmaz", "Roth", "Demir", "Nowak", "König", "Krüger", "Winkler",
            "Peters");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate =
        new Part(null, "P-1001", "Testfilter", "Filter", "T-01", 5, 2, new BigDecimal("14.9"));
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
