package course.musicschool.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.musicschool.service.MusicCourseService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(MusicCourseController.class)
class MusicCourseControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean MusicCourseService service;

  @Test
  void rejectsBlankCourseCode() throws Exception {
    mvc.perform(
            post("/api/courses")
                .contentType("application/json")
                .content("{\"courseCode\": \"\", \"title\": \"Testdatensatz\", \"fee\": 1.0}"))
        .andExpect(status().isBadRequest());
  }
}
