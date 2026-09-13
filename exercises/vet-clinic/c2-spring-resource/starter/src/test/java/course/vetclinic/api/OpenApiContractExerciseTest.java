package course.vetclinic.api;

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
        .andExpect(jsonPath("$.paths['/api/medications'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/medications/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/medications'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.pzn").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.productName").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.activeIngredient").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.dosageForm").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.prescriptionRequired").exists())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.active").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.pzn").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.productName").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.activeIngredient").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.dosageForm").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.prescriptionRequired").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.active").exists())
        .andExpect(jsonPath("$.components.schemas.CreateMedicationRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.MedicationResponse.properties.id.readOnly").value(true));
  }
}
