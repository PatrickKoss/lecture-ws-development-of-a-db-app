package course.musicschool.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO C2: nach findAll und findById aktivieren")
@SpringBootTest
class JpaRepositoryExerciseTest {
  @Autowired MusicCourseRepository repository;
  @Autowired LookupRepository lookups;

  @Test
  void readsSeedRowsThroughHibernateMappings() {
    assertThat(repository.findById(1)).isPresent();
    assertThat(repository.findAll()).isNotEmpty();
    assertThat(lookups.findAllLabels()).containsExactly("R-101", "R-204", "SAAL");
  }
}
