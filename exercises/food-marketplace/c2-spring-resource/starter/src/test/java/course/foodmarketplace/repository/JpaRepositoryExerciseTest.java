package course.foodmarketplace.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO C2: nach findAll und findById aktivieren")
@SpringBootTest
class JpaRepositoryExerciseTest {
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
}
