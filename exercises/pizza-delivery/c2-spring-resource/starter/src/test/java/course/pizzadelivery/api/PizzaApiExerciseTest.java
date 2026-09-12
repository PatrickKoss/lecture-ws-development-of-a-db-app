package course.pizzadelivery.api;

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
class PizzaApiExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/pizzas"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].pizzaNumber").value("P-01"));
  }

  @Test
  void createsAndRejectsDuplicateBusinessKeys() throws Exception {
    mvc.perform(
            post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"pizzaNumber\": \"P-99\", \"name\": \"Testpizza\", \"category\":"
                        + " \"Saisonal\", \"ovenStation\": \"OFEN-Z\", \"basePrice\": 11.5,"
                        + " \"active\": true}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    mvc.perform(
            post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"pizzaNumber\": \"P-01\", \"name\": \"Testpizza\", \"category\":"
                        + " \"Saisonal\", \"ovenStation\": \"OFEN-Z\", \"basePrice\": 11.5,"
                        + " \"active\": true}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("PIZZA_NUMBER_EXISTS"));
  }

  @Test
  void reportsValidationWithCorrelationId() throws Exception {
    mvc.perform(
            post("/api/pizzas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.pizzaNumber").exists());
  }
}
