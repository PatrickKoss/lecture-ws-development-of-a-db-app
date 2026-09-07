package course.musicschool.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.musicschool.domain.MusicCourse;
import course.musicschool.service.MusicCourseService;
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
  @MockitoBean MusicCourseService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/courses'].get").exists())
        .andExpect(jsonPath("$.paths['/api/courses/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/courses'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateMusicCourseRequest"))
        .andExpect(jsonPath("$.paths['/api/courses'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/MusicCourseResponse"))
        .andExpect(jsonPath("$.paths['/api/courses'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/courses'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/courses'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMusicCourseRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.MusicCourseResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(MusicCourse.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateMusicCourseRequest.class))).thenReturn(value);
    mvc.perform(post("/api/courses").contentType("application/json")
            .content("{\"courseCode\": \"MU-99\", \"title\": \"Songwriting\", \"fee\": 80.0}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/courses/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/courses").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
