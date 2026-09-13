package course.bikerental.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryExerciseTest {
  @Autowired StationRepository repository;
  @Autowired LookupRepository lookups;

  @Disabled("TODO C2: nach findAll aktivieren")
  @Test
  void readsSortedSeedRows() {
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(repository.findAll()).extracting(value -> value.id()).isSorted();
  }

  @Disabled("TODO C2: nach findById aktivieren")
  @Test
  void readsKnownIdAndReportsMissingId() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findById(Long.MAX_VALUE)).isEmpty();
  }

  @Test
  void readsPreparedLookupsInIdOrder() {
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
