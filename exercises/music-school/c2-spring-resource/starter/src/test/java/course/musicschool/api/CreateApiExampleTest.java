package course.musicschool.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CreateApiExampleTest {
  @Autowired MockMvc mvc;

  @Disabled("TODO C3: nach Implementierung von POST aktivieren")
  @Test
  void createsResource() throws Exception {
    var created = mvc.perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"courseCode\": \"MU-99\", \"title\": \"Songwriting\", \"fee\": 80}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$" + ".courseCode").value("MU-99"))
        .andReturn();
    mvc.perform(get(created.getResponse().getHeader("Location")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$" + ".courseCode").value("MU-99"));
  }

  @Disabled("TODO C3: Test schreiben und danach aktivieren")
  @Test
  void rejectsDuplicateBusinessKey() throws Exception {
    // TODO C3: Sendet einen vorhandenen Fachschlüssel und prüft Status 409 sowie den Fehlercode.
    throw new AssertionError("TODO C3: Konflikttest schreiben");
  }

  @Disabled("TODO C3: nach Bean-Validation aktivieren")
  @Test
  void reportsValidationWithCorrelationId() throws Exception {
    mvc.perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.courseCode").exists());
  }
}
