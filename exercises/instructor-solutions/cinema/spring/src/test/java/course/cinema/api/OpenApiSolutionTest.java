package course.cinema.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.cinema.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiSolutionTest {
  @Autowired MockMvc mvc;
  @MockitoBean MovieService service;

  @Test
  void generatesCompleteContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/movies'].get").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies'].get.responses['200'].content['application/json'].schema.type")
                .value("array"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies'].get.responses['200'].content['application/json'].schema.items['$ref']")
                .value("#/components/schemas/MovieResponse"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies/{id}'].get.responses['404'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies'].post.requestBody.content['application/json'].schema['$ref']")
                .value("#/components/schemas/CreateMovieRequest"))
        .andExpect(jsonPath("$.paths['/api/movies'].post.responses['201'].headers.Location").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies'].post.responses['400'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/movies'].post.responses['409'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.components.schemas.CreateMovieRequest.properties.id").doesNotExist())
        .andExpect(
            jsonPath("$.components.schemas.CreateMovieRequest.properties.movieCode.example")
                .value("F-199"))
        .andExpect(jsonPath("$.components.schemas.MovieResponse.properties.id.readOnly").value(true));
  }
}
