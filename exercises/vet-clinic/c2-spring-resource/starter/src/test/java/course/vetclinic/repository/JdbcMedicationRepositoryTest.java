package course.vetclinic.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO(C2): Nach der GET-Implementierung aktivieren")
@SpringBootTest
class JdbcMedicationRepositoryTest {
  @Autowired JdbcMedicationRepository repository;

  @Test
  void readsKnownB2Data() {
    assertTrue(repository.findAll().size() >= 3);
    assertEquals("09231122", repository.findById(1).orElseThrow().pzn());
    assertTrue(repository.findById(99999).isEmpty());
  }
}
