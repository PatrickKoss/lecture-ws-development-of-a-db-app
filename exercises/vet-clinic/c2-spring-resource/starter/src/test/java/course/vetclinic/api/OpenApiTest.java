package course.vetclinic.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.vetclinic.domain.Medication;
import course.vetclinic.service.MedicationService;
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
  @MockitoBean MedicationService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/medications'].get").exists())
        .andExpect(jsonPath("$.paths['/api/medications/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/medications'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateMedicationRequest"))
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/MedicationResponse"))
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Medication.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateMedicationRequest.class))).thenReturn(value);
    mvc.perform(post("/api/medications").contentType("application/json")
            .content("{\"pzn\": \"00999999\", \"productName\": \"Kursmed\", \"activeIngredient\": \"Teststoff\", \"dosageForm\": \"Tablette\", \"prescriptionRequired\": true, \"active\": true}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/medications/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/medications").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
