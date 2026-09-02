package course.pizzadelivery.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.pizzadelivery.service.PizzaService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(PizzaController.class)
class PizzaControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean PizzaService service;

  @Test
  void rejectsBlankPizzaNumber() throws Exception {
    mvc.perform(
            post("/api/pizzas")
                .contentType("application/json")
                .content(
                    "{\"pizzaNumber\": \"\", \"name\": \"Kurspizza\", \"category\": \"Spezial\","
                        + " \"ovenStation\": \"OFEN-A\", \"basePrice\": 11.5, \"active\": true}"))
        .andExpect(status().isBadRequest());
  }
}
