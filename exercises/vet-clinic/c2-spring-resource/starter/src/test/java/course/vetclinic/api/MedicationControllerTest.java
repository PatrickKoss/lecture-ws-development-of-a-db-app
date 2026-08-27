package course.vetclinic.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import course.vetclinic.service.MedicationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO(C3): Nach GET und POST aktivieren")
@WebMvcTest(MedicationController.class)
class MedicationControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean MedicationService service;

  @Test
  void rejectsBlankPzn() throws Exception {
    mvc.perform(
            post("/api/medications")
                .contentType("application/json")
                .content(
                    "{\"pzn\": \"\", \"productName\": \"Kursmed\", \"activeIngredient\":"
                        + " \"Teststoff\", \"dosageForm\": \"Tablette\", \"prescriptionRequired\":"
                        + " true, \"active\": true}"))
        .andExpect(status().isBadRequest());
  }
}
