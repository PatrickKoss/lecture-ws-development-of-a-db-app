package course.carworkshop.api;

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
class PartApiExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/parts"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].partNumber").value("P-1001"));
  }

  @Test
  void createsAndRejectsDuplicateBusinessKeys() throws Exception {
    mvc.perform(
            post("/api/parts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partNumber\": \"P-9999\", \"name\": \"Testfilter\", \"category\":"
                        + " \"Filter\", \"shelfCode\": \"T-01\", \"stockQuantity\": 5,"
                        + " \"reorderLevel\": 2, \"listPrice\": 14.9}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    mvc.perform(
            post("/api/parts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partNumber\": \"P-1001\", \"name\": \"Testfilter\", \"category\":"
                        + " \"Filter\", \"shelfCode\": \"T-01\", \"stockQuantity\": 5,"
                        + " \"reorderLevel\": 2, \"listPrice\": 14.9}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("PART_NUMBER_EXISTS"));
  }

  @Test
  void reportsValidationWithCorrelationId() throws Exception {
    mvc.perform(
            post("/api/parts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.partNumber").exists());
  }
}
