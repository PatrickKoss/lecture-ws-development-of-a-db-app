package course.gym.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LookupRepositoryTest {
  @Autowired LookupRepository repository;

  @Test
  void migrationsProvideDomainLookupData() {
    org.junit.jupiter.api.Assertions.assertTrue(repository.findAllLabels().size() >= 3);
  }
}
