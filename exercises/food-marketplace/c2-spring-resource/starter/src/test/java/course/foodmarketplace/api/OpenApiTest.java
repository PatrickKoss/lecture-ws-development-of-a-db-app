package course.foodmarketplace.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.foodmarketplace.domain.Restaurant;
import course.foodmarketplace.service.RestaurantService;
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
  @MockitoBean RestaurantService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/restaurants'].get").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreateRestaurantRequest"))
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/RestaurantResponse"))
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/restaurants'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRestaurantRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.RestaurantResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Restaurant.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreateRestaurantRequest.class))).thenReturn(value);
    mvc.perform(post("/api/restaurants").contentType("application/json")
            .content("{\"partnerNumber\": \"R-199\", \"name\": \"Kursküche\", \"street\": \"Kursweg 1\", \"postalCode\": \"45127\", \"city\": \"Essen\", \"commissionRate\": 17.5, \"active\": true}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/restaurants/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/restaurants").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
