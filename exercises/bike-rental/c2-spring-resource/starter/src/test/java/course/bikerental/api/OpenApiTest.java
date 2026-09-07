package course.bikerental.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.bikerental.domain.Station;
import course.bikerental.service.StationService;
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
  @MockitoBean StationService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/stations'].get").exists())
        .andExpect(jsonPath("$.paths['/api/stations/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/stations'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateStationRequest"))
        .andExpect(jsonPath("$.paths['/api/stations'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/StationResponse"))
        .andExpect(jsonPath("$.paths['/api/stations'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/stations'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/stations'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateStationRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.StationResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Station.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateStationRequest.class))).thenReturn(value);
    mvc.perform(post("/api/stations").contentType("application/json")
            .content("{\"stationCode\": \"MS-NEW\", \"name\": \"Kursstation\", \"address\": \"Kursweg 1, 48143 Münster\", \"capacity\": 20, \"status\": \"ACTIVE\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/stations/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/stations").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
