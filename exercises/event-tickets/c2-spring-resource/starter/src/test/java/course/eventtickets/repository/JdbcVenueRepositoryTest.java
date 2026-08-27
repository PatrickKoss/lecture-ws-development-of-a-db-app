package course.eventtickets.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO(C2): Nach der GET-Implementierung aktivieren")
@SpringBootTest
class JdbcVenueRepositoryTest {
  @Autowired JdbcVenueRepository repository;

  @Test
  void readsKnownB2Data() {
    assertTrue(repository.findAll().size() >= 3);
    assertEquals("V-01", repository.findById(1).orElseThrow().venueCode());
    assertTrue(repository.findById(99999).isEmpty());
  }
}
