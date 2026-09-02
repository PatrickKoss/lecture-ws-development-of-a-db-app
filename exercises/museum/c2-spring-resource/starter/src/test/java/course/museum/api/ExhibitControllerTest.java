package course.museum.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.museum.service.ExhibitService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(ExhibitController.class)
class ExhibitControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean ExhibitService service;

  @Test
  void rejectsBlankInventoryCode() throws Exception {
    mvc.perform(
            post("/api/exhibits")
                .contentType("application/json")
                .content("{\"inventoryCode\": \"\", \"title\": \"Testdatensatz\", \"insuredValue\": 1.0}"))
        .andExpect(status().isBadRequest());
  }
}
