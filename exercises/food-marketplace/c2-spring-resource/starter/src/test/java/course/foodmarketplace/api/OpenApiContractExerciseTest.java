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
@org.junit.jupiter.api.Disabled("TODO C1: nach Ergänzung des HTTP-Vertrags aktivieren")
class OpenApiContractExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void generatesContractWithoutCallingUnfinishedRepositoryMethods() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/restaurants'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.partnerNumber").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.street").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.postalCode").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.city").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.commissionRate").exists())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.active").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.partnerNumber").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.street").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.postalCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.commissionRate").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.active").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.id.readOnly").value(true));
  }
}
