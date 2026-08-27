package course.library.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.library.service.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(BookController.class)
class BookControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean BookService service;

  @Test
  void rejectsBlankIsbn() throws Exception {
    mvc.perform(
            post("/api/books")
                .contentType("application/json")
                .content(
                    "{\"isbn\": \"\", \"title\": \"Kursbuch\", \"publicationYear\": 2026,"
                        + " \"subjectArea\": \"Fachbuch\", \"shelfCode\": \"F-10\"}"))
        .andExpect(status().isBadRequest());
  }
}
