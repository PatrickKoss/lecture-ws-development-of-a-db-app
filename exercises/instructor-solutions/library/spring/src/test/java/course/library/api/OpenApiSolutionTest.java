package course.library.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.library.service.BookService;
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
  @MockitoBean BookService service;

  @Test
  void generatesCompleteContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/books'].get").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/books'].get.responses['200'].content['application/json'].schema.type")
                .value("array"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/books'].get.responses['200'].content['application/json'].schema.items['$ref']")
                .value("#/components/schemas/BookResponse"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/books/{id}'].get.responses['404'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/books'].post.requestBody.content['application/json'].schema['$ref']")
                .value("#/components/schemas/CreateBookRequest"))
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['201'].headers.Location").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/books'].post.responses['400'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/books'].post.responses['409'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.id").doesNotExist())
        .andExpect(
            jsonPath("$.components.schemas.CreateBookRequest.properties.isbn.example")
                .value("9780000000000"))
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.id.readOnly").value(true));
  }
}
