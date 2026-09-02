package course.carworkshop.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.carworkshop.service.PartService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(PartController.class)
class PartControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean PartService service;

  @Test
  void rejectsBlankPartNumber() throws Exception {
    mvc.perform(
            post("/api/parts")
                .contentType("application/json")
                .content(
                    "{\"partNumber\": \"\", \"name\": \"Kursfilter\", \"category\": \"Filter\","
                        + " \"shelfCode\": \"Z-99\", \"stockQuantity\": 10, \"reorderLevel\": 4,"
                        + " \"listPrice\": 12.5}"))
        .andExpect(status().isBadRequest());
  }
}
