package course.foodmarketplace.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.foodmarketplace.service.RestaurantService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean RestaurantService service;

  @Test
  void rejectsBlankPartnerNumber() throws Exception {
    mvc.perform(
            post("/api/restaurants")
                .contentType("application/json")
                .content(
                    "{\"partnerNumber\": \"\", \"name\": \"Kursküche\", \"street\": \"Kursweg 1\","
                        + " \"postalCode\": \"45127\", \"city\": \"Essen\", \"commissionRate\":"
                        + " 17.5, \"active\": true}"))
        .andExpect(status().isBadRequest());
  }
}
