package course.vetclinic.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiContractTest {
  @Autowired MockMvc mvc;

  @Test
  void documentsAndExportsTheImplementedContract() throws Exception {
    var result =
        mvc.perform(get("/v3/api-docs"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.paths['/api/medications'].get").exists())
            .andExpect(jsonPath("$.paths['/api/medications'].post").exists())
            .andExpect(jsonPath("$.paths['/api/medications/{id}'].get").exists())
            .andExpect(jsonPath("$.paths['/api/medications/{id}'].put").exists())
            .andExpect(jsonPath("$.paths['/api/medications/{id}'].delete").exists())
            .andExpect(
                jsonPath("$.components.schemas.CreateMedicationRequest.properties.id")
                    .doesNotExist())
            .andExpect(
                jsonPath("$.components.schemas.MedicationResponse.properties.id.readOnly")
                    .value(true))
            .andReturn();
    var document = result.getResponse().getContentAsString();
    assertThat(document).contains("ApiError", "X-Correlation-ID");
    var output = Path.of("build/openapi/openapi.json");
    Files.createDirectories(output.getParent());
    Files.writeString(output, document);
  }
}
