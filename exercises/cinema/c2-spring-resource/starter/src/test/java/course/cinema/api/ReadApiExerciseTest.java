package course.cinema.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ReadApiExerciseTest {
  @Autowired MockMvc mvc;

  @Disabled("TODO C2: nach Implementierung von GET /api aktivieren")
  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/movies"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].movieCode").value("F-101"));
  }

  @Disabled("TODO C3: Test schreiben und danach aktivieren")
  @Test
  void reportsUnknownId() throws Exception {
    // TODO C3: Fordert eine unbekannte ID an und prüft Status 404 sowie den Fehlercode.
    throw new AssertionError("TODO C3: 404-Test schreiben");
  }
}
