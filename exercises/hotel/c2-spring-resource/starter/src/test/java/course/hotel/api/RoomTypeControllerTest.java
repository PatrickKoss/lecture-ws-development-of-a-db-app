package course.hotel.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.hotel.service.RoomTypeService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(RoomTypeController.class)
class RoomTypeControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean RoomTypeService service;

  @Test
  void rejectsBlankTypeCode() throws Exception {
    mvc.perform(
            post("/api/room-types")
                .contentType("application/json")
                .content(
                    "{\"typeCode\": \"\", \"name\": \"Kurszimmer\", \"capacity\": 2,"
                        + " \"standardPriceCents\": 14900}"))
        .andExpect(status().isBadRequest());
  }
}
