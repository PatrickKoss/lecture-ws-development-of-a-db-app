package course.parceldelivery.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.parceldelivery.domain.Parcel;
import course.parceldelivery.service.ParcelService;
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
  @MockitoBean ParcelService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/parcels'].get").exists())
        .andExpect(jsonPath("$.paths['/api/parcels/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/parcels'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateParcelRequest"))
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ParcelResponse"))
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateParcelRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Parcel.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateParcelRequest.class))).thenReturn(value);
    mvc.perform(post("/api/parcels").contentType("application/json")
            .content("{\"trackingCode\": \"PK-99\", \"recipient\": \"Testdatensatz\", \"weight\": 1.0}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/parcels/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/parcels").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
