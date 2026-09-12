package course.musicschool.repository;

import static org.junit.jupiter.api.Assertions.*;

import course.musicschool.domain.MusicCourse;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JdbcMusicCourseRepositoryTest {
  @Autowired JdbcMusicCourseRepository repository;

  @Test
  void readsKnownB2Data() {
    assertTrue(repository.findAll().size() >= 4);
    assertEquals("MU-01", repository.findById(1).orElseThrow().courseCode());
    assertTrue(repository.findById(99999).isEmpty());
  }

  @Test
  void insertsCourseAndDetectsItsCode() {
    var created =
        repository.insert(
            new MusicCourse(null, "SOL-01", "Testkurs", new BigDecimal("40.00")));

    assertNotNull(created.id());
    assertEquals("SOL-01", repository.findById(created.id()).orElseThrow().courseCode());
    assertTrue(repository.existsByCourseCode("SOL-01"));
    assertFalse(repository.existsByCourseCode("UNKNOWN"));
  }
}
