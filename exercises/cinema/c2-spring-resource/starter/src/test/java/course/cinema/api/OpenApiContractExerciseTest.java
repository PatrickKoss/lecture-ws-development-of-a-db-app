package course.cinema.api;

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
        .andExpect(jsonPath("$.paths['/api/movies'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/movies/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/movies'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/movies'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/movies'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.movieCode").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.title").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.releaseYear").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.durationMinutes").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.fskCode").exists())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.minimumAge").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.movieCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.title").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.releaseYear").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.durationMinutes").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.fskCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.minimumAge").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.id.readOnly").value(true));
  }
}
