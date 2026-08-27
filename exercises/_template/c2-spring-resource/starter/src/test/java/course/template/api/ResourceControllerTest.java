package course.template.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.template.service.ResourceService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(ResourceController.class)
class ResourceControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean ResourceService service;

  @Test
  void rejectsBlankResourceCode() throws Exception {
    mvc.perform(
            post("/api/resources")
                .contentType("application/json")
                .content("{\"resourceCode\": \"\", \"name\": \"Testdatensatz\", \"measure\": 1.0}"))
        .andExpect(status().isBadRequest());
  }
}
