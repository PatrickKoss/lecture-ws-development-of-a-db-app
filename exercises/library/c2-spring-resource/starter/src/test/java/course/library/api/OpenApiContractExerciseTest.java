package course.library.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@org.junit.jupiter.api.Disabled("TODO C1: nach Ergänzung des HTTP-Vertrags aktivieren")
class OpenApiContractExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void generatesContractWithoutCallingUnfinishedRepositoryMethods() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/books'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/books/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.isbn").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.title").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.publicationYear").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.subjectArea").exists())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.shelfCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.isbn").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.title").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.publicationYear").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.subjectArea").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.shelfCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.id.readOnly").value(true));
  }
}
