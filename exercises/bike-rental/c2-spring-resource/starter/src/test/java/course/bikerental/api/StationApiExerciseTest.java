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

@Disabled("TODO C2/C3: nach Implementierung der JPA-Adaptermethoden aktivieren")
@SpringBootTest
@AutoConfigureMockMvc
class StationApiExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/stations"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].stationCode").value("DOM"));
  }

  @Test
  void createsAndRejectsDuplicateBusinessKeys() throws Exception {
    mvc.perform(
            post("/api/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"stationCode\": \"N99\", \"name\": \"Neue Station\", \"address\": \"Neue"
                        + " Straße 1, Münster\", \"capacity\": 12, \"status\": \"PLANNED\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    mvc.perform(
            post("/api/stations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"stationCode\": \"DOM\", \"name\": \"Neue Station\", \"address\": \"Neue"
                        + " Straße 1, Münster\", \"capacity\": 12, \"status\": \"PLANNED\"}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("STATION_CODE_EXISTS"));
  }

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
