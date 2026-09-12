package course.foodmarketplace.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RestaurantApiIntegrationTest {
  private static final String BASE = "/api/restaurants";
  private static final String VALID =
      "{\"partnerNumber\": \"R-999\", \"name\": \"Testküche\", \"street\": \"Markt 1\","
          + " \"postalCode\": \"45127\", \"city\": \"Essen\", \"commissionRate\": 12.5, \"active\":"
          + " true}";

  @Autowired MockMvc mvc;

  @Test
  void readsSeedDataAndReportsMissingIds() throws Exception {
    mvc.perform(get(BASE))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].partnerNumber").value("R-101"));
    mvc.perform(get(BASE + "/999999").header("X-Correlation-ID", "read-404"))
        .andExpect(status().isNotFound())
        .andExpect(header().string("X-Correlation-ID", "read-404"))
        .andExpect(jsonPath("$.code").value("RESTAURANT_NOT_FOUND"))
        .andExpect(jsonPath("$.correlationId").value("read-404"));
  }

  @Test
  void createsReplacesAndDeletesThroughRealPersistence() throws Exception {
    var created =
        mvc.perform(post(BASE).contentType(MediaType.APPLICATION_JSON).content(VALID))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.partnerNumber").value("R-999"))
            .andReturn();
    var location = created.getResponse().getHeader("Location");
    long id = Long.parseLong(location.substring(location.lastIndexOf('/') + 1));

    mvc.perform(get(URI.create(location))).andExpect(status().isOk());
    mvc.perform(
            put(BASE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-999\", \"name\": \"Testküche aktualisiert\","
                        + " \"street\": \"Markt 1\", \"postalCode\": \"45127\", \"city\":"
                        + " \"Essen\", \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(id))
        .andExpect(jsonPath("$.name").value("Testküche aktualisiert"));
    mvc.perform(get(BASE + "/" + id))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Testküche aktualisiert"));
    mvc.perform(
            put(BASE + "/999999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-999\", \"name\": \"Testküche aktualisiert\","
                        + " \"street\": \"Markt 1\", \"postalCode\": \"45127\", \"city\":"
                        + " \"Essen\", \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isNotFound());
    mvc.perform(
            put(BASE + "/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-101\", \"name\": \"Testküche aktualisiert\","
                        + " \"street\": \"Markt 1\", \"postalCode\": \"45127\", \"city\":"
                        + " \"Essen\", \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("PARTNER_NUMBER_EXISTS"));
    mvc.perform(get(BASE + "/" + id))
        .andExpect(jsonPath("$.partnerNumber").value("R-999"))
        .andExpect(jsonPath("$.name").value("Testküche aktualisiert"));
    mvc.perform(delete(BASE + "/" + id)).andExpect(status().isNoContent());
    mvc.perform(delete(BASE + "/" + id)).andExpect(status().isNoContent());
    mvc.perform(get(BASE + "/" + id)).andExpect(status().isNotFound());
  }

  @Test
  void returnsStableClientErrorsAndSafeCorrelationIds() throws Exception {
    mvc.perform(
            post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "validation-42"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "validation-42"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("validation-42"))
        .andExpect(jsonPath("$.fields.partnerNumber").exists());
    mvc.perform(
            post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{")
                .header("X-Correlation-ID", "bad id with spaces"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("MALFORMED_JSON"))
        .andExpect(
            header().string("X-Correlation-ID", org.hamcrest.Matchers.not("bad id with spaces")));
    mvc.perform(post(BASE).contentType(MediaType.TEXT_PLAIN).content(VALID))
        .andExpect(status().isUnsupportedMediaType())
        .andExpect(jsonPath("$.code").value("UNSUPPORTED_MEDIA_TYPE"));
    mvc.perform(get(BASE + "/not-a-number"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("INVALID_REQUEST_PARAMETER"));
    mvc.perform(get(BASE + "/0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"));
    mvc.perform(patch(BASE))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(header().exists("Allow"))
        .andExpect(jsonPath("$.code").value("METHOD_NOT_ALLOWED"));
    mvc.perform(get(BASE).accept(MediaType.APPLICATION_XML)).andExpect(status().isNotAcceptable());
    mvc.perform(get("/api/route-does-not-exist"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.code").value("ROUTE_NOT_FOUND"));
  }

  @Test
  void rejectsDuplicateBusinessKey() throws Exception {
    mvc.perform(
            post(BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    "{\"partnerNumber\": \"R-101\", \"name\": \"Testküche aktualisiert\","
                        + " \"street\": \"Markt 1\", \"postalCode\": \"45127\", \"city\":"
                        + " \"Essen\", \"commissionRate\": 12.5, \"active\": true}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("PARTNER_NUMBER_EXISTS"));
  }

  @Test
  void returnsConflictWhenSeedReferencesPreventDelete() throws Exception {
    mvc.perform(delete(BASE + "/1"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("DATA_INTEGRITY_VIOLATION"));
    mvc.perform(get(BASE + "/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.partnerNumber").value("R-101"));
  }
}
