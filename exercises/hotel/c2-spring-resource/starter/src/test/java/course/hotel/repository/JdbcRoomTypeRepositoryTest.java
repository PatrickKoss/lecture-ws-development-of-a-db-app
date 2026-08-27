package course.hotel.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("TODO(C2): Nach der GET-Implementierung aktivieren")
@SpringBootTest
class JdbcRoomTypeRepositoryTest {
  @Autowired JdbcRoomTypeRepository repository;

  @Test
  void readsKnownB2Data() {
    assertTrue(repository.findAll().size() >= 3);
    assertEquals("EZ", repository.findById(1).orElseThrow().typeCode());
    assertTrue(repository.findById(99999).isEmpty());
  }
}
