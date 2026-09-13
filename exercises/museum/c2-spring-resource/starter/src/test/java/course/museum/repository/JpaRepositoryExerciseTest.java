package course.museum.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JpaRepositoryExerciseTest {
  @Autowired ExhibitRepository repository;
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
        .containsExactly("Stadtgeschichte", "Kunst des 20. Jahrhunderts", "Sonderausstellung");
  }
}
