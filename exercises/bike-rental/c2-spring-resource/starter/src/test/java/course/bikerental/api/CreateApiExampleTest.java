package course.bikerental.api;

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
            post("/api/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"stationCode\": \"N99\", \"name\": \"Neue Station\", \"address\": \"Neue"
                        + " Straße 1, Münster\", \"capacity\": 12, \"status\": \"PLANNED\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").isNumber())
        .andExpect(jsonPath("$" + ".stationCode").value("N99"))
        .andReturn();
    mvc.perform(get(created.getResponse().getHeader("Location")))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$" + ".stationCode").value("N99"));
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
            post("/api/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.stationCode").exists());
  }
}
