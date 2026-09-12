package course.musicschool.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO C2/C3: nach Implementierung der JPA-Adaptermethoden aktivieren")
@SpringBootTest
@AutoConfigureMockMvc
class MusicCourseApiExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void readsSeedData() throws Exception {
    mvc.perform(get("/api/courses"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].courseCode").value("MU-01"));
  }

  @Test
  void createsAndRejectsDuplicateBusinessKeys() throws Exception {
    mvc.perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"courseCode\": \"MU-99\", \"title\": \"Songwriting\", \"fee\": 80}"))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"));
    mvc.perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"courseCode\": \"MU-01\", \"title\": \"Songwriting\", \"fee\": 80}"))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("MUSIC_COURSE_CODE_EXISTS"));
  }

  @Test
  void reportsValidationWithCorrelationId() throws Exception {
    mvc.perform(
            post("/api/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .header("X-Correlation-ID", "exercise-validation"))
        .andExpect(status().isBadRequest())
        .andExpect(header().string("X-Correlation-ID", "exercise-validation"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
        .andExpect(jsonPath("$.fields.courseCode").exists());
  }
}
