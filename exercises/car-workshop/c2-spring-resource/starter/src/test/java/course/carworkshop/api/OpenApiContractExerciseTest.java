package course.carworkshop.api;

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
        .andExpect(jsonPath("$.paths['/api/parts'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/parts/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/parts'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/parts'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/parts'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.partNumber").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.category").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.shelfCode").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.stockQuantity").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.reorderLevel").exists())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.listPrice").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.partNumber").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.category").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.shelfCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.stockQuantity").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.reorderLevel").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.listPrice").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePartRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.PartResponse.properties.id.readOnly").value(true));
  }
}
