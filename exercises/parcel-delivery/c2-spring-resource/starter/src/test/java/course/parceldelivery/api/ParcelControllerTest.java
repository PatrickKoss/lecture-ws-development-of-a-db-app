package course.parceldelivery.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.parceldelivery.service.ParcelService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(ParcelController.class)
class ParcelControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean ParcelService service;

  @Test
  void rejectsBlankTrackingCode() throws Exception {
    mvc.perform(
            post("/api/parcels")
                .contentType("application/json")
                .content("{\"trackingCode\": \"\", \"recipient\": \"Testdatensatz\", \"weight\": 1.0}"))
        .andExpect(status().isBadRequest());
  }
}
