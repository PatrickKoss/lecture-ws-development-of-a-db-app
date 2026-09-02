package course.cinema.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.cinema.service.MovieService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(MovieController.class)
class MovieControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean MovieService service;

  @Test
  void rejectsBlankMovieCode() throws Exception {
    mvc.perform(
            post("/api/movies")
                .contentType("application/json")
                .content(
                    "{\"movieCode\": \"\", \"title\": \"Kursfilm\", \"releaseYear\": 2026,"
                        + " \"durationMinutes\": 90, \"fskCode\": \"FSK_12\", \"minimumAge\": 12}"))
        .andExpect(status().isBadRequest());
  }
}
