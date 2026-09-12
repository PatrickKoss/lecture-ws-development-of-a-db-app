package course.foodmarketplace.api;

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
class RestaurantApiExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/restaurants"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].partnerNumber").value("R-101"));
  }

  @Test
  void createsAndRejectsDuplicateBusinessKeys() throws Exception {
    mvc.perform(
            post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-999\", \"name\": \"Testküche\", \"street\": \"Markt"
                        + " 1\", \"postalCode\": \"45127\", \"city\": \"Essen\","
                        + " \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    mvc.perform(
            post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-101\", \"name\": \"Testküche\", \"street\": \"Markt"
                        + " 1\", \"postalCode\": \"45127\", \"city\": \"Essen\","
                        + " \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("PARTNER_NUMBER_EXISTS"));
  }

  @Test
  void reportsValidationWithCorrelationId() throws Exception {
    mvc.perform(
            post("/api/restaurants")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.partnerNumber").exists());
  }
}
