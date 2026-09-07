package course.pizzadelivery.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import course.pizzadelivery.domain.Pizza;
import course.pizzadelivery.service.PizzaService;
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
  @MockitoBean PizzaService service;

  @Test
  void generatesContractFromControllerAndModels() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/pizzas'].get").exists())
        .andExpect(jsonPath("$.paths['/api/pizzas/{id}'].get").exists())
        .andExpect(jsonPath("$.paths['/api/pizzas'].post.requestBody.content['application/json'].schema['$ref']")
            .value("#/components/schemas/CreatePizzaRequest"))
        .andExpect(jsonPath("$.paths['/api/pizzas'].post.responses['201'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/PizzaResponse"))
        .andExpect(jsonPath("$.paths['/api/pizzas'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/pizzas'].post.responses['400'].content['application/json'].schema['$ref']")
            .value("#/components/schemas/ApiError"))
        .andExpect(jsonPath("$.paths['/api/pizzas'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.CreatePizzaRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.PizzaResponse.properties.id.readOnly").value(true));
    mvc.perform(get("/swagger-ui.html")).andExpect(status().is3xxRedirection());
    mvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk());
  }

  @Test
  void createsResponseWithLocation() throws Exception {
    var value = mock(Pizza.class);
    when(value.id()).thenReturn(42L);
    when(service.create(any(CreatePizzaRequest.class))).thenReturn(value);
    mvc.perform(post("/api/pizzas").contentType("application/json")
            .content("{\"pizzaNumber\": \"P-99\", \"name\": \"Kurspizza\", \"category\": \"Spezial\", \"ovenStation\": \"OFEN-A\", \"basePrice\": 11.5, \"active\": true}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/pizzas/42"))
        .andExpect(jsonPath("$.id").value(42));
  }

  @Test
  void validatesBeforeCallingService() throws Exception {
    mvc.perform(post("/api/pizzas").contentType("application/json").content("{}"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    verifyNoInteractions(service);
  }
}
