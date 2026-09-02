package course.eventtickets.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.eventtickets.service.VenueService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(VenueController.class)
class VenueControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean VenueService service;

  @Test
  void rejectsBlankVenueCode() throws Exception {
    mvc.perform(
            post("/api/venues")
                .contentType("application/json")
                .content(
                    "{\"venueCode\": \"\", \"name\": \"Kursort\", \"street\": \"Kursweg 1\","
                        + " \"postalCode\": \"45127\", \"city\": \"Essen\", \"capacity\": 250}"))
        .andExpect(status().isBadRequest());
  }
}
