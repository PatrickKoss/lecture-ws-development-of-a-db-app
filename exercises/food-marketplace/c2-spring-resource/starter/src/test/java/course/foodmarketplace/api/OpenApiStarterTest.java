package course.foodmarketplace.api;

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
class OpenApiStarterTest {
  @Autowired MockMvc mvc;

  @Test
  void generatesContractWithoutCallingUnfinishedRepositoryMethods() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post").exists())
        .andExpect(
            jsonPath("$.components.schemas.CreateRestaurantRequest.properties.id").doesNotExist())
        .andExpect(
            jsonPath("$.components.schemas.RestaurantResponse.properties.id.readOnly").value(true));
  }
}
