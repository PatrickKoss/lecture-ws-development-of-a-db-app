package course.parceldelivery.api;

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
        .andExpect(jsonPath("$.paths['/api/parcels'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/parcels/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/parcels'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.trackingCode").exists())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.recipient").exists())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.weight").exists())
        .andExpect(jsonPath("$.components.schemas.CreateParcelRequest.properties.trackingCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateParcelRequest.properties.recipient").exists())
        .andExpect(jsonPath("$.components.schemas.CreateParcelRequest.properties.weight").exists())
        .andExpect(jsonPath("$.components.schemas.CreateParcelRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.ParcelResponse.properties.id.readOnly").value(true));
  }
}
