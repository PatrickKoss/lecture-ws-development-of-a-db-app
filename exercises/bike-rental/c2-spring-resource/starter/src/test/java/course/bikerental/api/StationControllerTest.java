package course.bikerental.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.bikerental.service.StationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(StationController.class)
class StationControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean StationService service;

  @Test
  void rejectsBlankStationCode() throws Exception {
    mvc.perform(
            post("/api/stations")
                .contentType("application/json")
                .content(
                    "{\"stationCode\": \"\", \"name\": \"Kursstation\", \"address\": \"Kursweg 1,"
                        + " 48143 Münster\", \"capacity\": 20, \"status\": \"ACTIVE\"}"))
        .andExpect(status().isBadRequest());
  }
}
