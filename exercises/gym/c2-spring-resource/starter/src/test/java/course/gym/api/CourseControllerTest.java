package course.gym.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.gym.service.CourseService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(CourseController.class)
class CourseControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean CourseService service;

  @Test
  void rejectsBlankCourseCode() throws Exception {
    mvc.perform(
            post("/api/courses")
                .contentType("application/json")
                .content(
                    "{\"courseCode\": \"\", \"title\": \"Kursformat\", \"level\": \"BEGINNER\","
                        + " \"durationMinutes\": 45, \"roomId\": 1}"))
        .andExpect(status().isBadRequest());
  }
}
