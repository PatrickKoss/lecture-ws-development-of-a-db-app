package course.library.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.library.domain.Book;
import course.library.service.BookService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiTest {
  @Autowired MockMvc mvc;
  @MockitoBean BookService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/books'].get").exists())
        .andExpect(jsonPath("$.paths['/api/books/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/books'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateBookRequest"))
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/BookResponse"))
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/books'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateBookRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.BookResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Book.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateBookRequest.class))).thenReturn(value);
    mvc.perform(post("/api/books").contentType("application/json")
            .content("{\"isbn\": \"9780000000000\", \"title\": \"Kursbuch\", \"publicationYear\": 2026, \"subjectArea\": \"Fachbuch\", \"shelfCode\": \"F-10\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/books/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/books").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
