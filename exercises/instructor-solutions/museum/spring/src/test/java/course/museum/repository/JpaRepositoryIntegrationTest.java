package course.museum.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import course.museum.domain.Exhibit;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryIntegrationTest {
  @Autowired ExhibitRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly("Stadtgeschichte", "Kunst des 20. Jahrhunderts", "Sonderausstellung");
  }

  @Test
  void translatesARealSQLiteConstraintAtThePersistenceBoundary() {
    var duplicate = new Exhibit(null, "EX-01", "Testexponat", new BigDecimal("1000"));
    assertThatThrownBy(() -> repository.save(duplicate))
        .isInstanceOf(PersistenceConstraintException.class);
  }
}
