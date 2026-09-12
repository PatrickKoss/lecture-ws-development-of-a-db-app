package course.bikerental.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO C2: nach findAll und findById aktivieren")
@SpringBootTest
class JpaRepositoryExerciseTest {
  @Autowired StationRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels())
        .containsExactly(
            "Arroyo C7+",
            "Endeavour 1.B Move",
            "Touring Hybrid ONE 500",
            "City Flight",
            "247",
            "Charger4",
            "FX 2 Disc",
            "C Line Explore",
            "City Mountain",
            "Quick 4");
  }
}
