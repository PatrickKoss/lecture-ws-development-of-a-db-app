package course.foodmarketplace.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.foodmarketplace.service.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiSolutionTest {
  @Autowired MockMvc mvc;
  @MockitoBean RestaurantService service;

  @Test
  void generatesCompleteContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/restaurants'].get").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants'].get.responses['200'].content['application/json'].schema.type")
                .value("array"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants'].get.responses['200'].content['application/json'].schema.items['$ref']")
                .value("#/components/schemas/RestaurantResponse"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants/{id}'].get.responses['404'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants'].post.requestBody.content['application/json'].schema['$ref']")
                .value("#/components/schemas/CreateRestaurantRequest"))
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['201'].headers.Location").exists())
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants'].post.responses['400'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(
            jsonPath(
                    "$.paths['/api/restaurants'].post.responses['409'].content['application/json'].schema['$ref']")
                .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.id").doesNotExist())
        .andExpect(
            jsonPath("$.components.schemas.CreateRestaurantRequest.properties.partnerNumber.example")
                .value("R-199"))
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.id.readOnly").value(true));
  }
}
