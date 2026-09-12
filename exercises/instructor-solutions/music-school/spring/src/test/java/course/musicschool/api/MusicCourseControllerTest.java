package course.musicschool.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import course.musicschool.domain.MusicCourse;
import course.musicschool.service.MusicCourseService;
import course.musicschool.web.ConflictException;
import course.musicschool.web.CorrelationIdFilter;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MusicCourseController.class)
class MusicCourseControllerTest {
  @Autowired MockMvc mvc;
  @MockitoBean MusicCourseService service;

  @Test
  void createsCourseWithLocationHeader() throws Exception {
    given(service.create(any()))
        .willReturn(new MusicCourse(5L, "MU-99", "Songwriting", new BigDecimal("80.0")));

    mvc.perform(
            post("/api/courses")
                .contentType("application/json")
                .content("""
                    {"courseCode":"MU-99","title":"Songwriting","fee":80.0}
                    """))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/api/courses/5"))
        .andExpect(jsonPath("$.id").value(5))
        .andExpect(jsonPath("$.courseCode").value("MU-99"));
  }

  @Test
  void rejectsBlankCourseCode() throws Exception {
    mvc.perform(
            post("/api/courses")
                .header(CorrelationIdFilter.HEADER, "validation-test")
                .contentType("application/json")
                .content("""
                    {"courseCode":"","title":"Songwriting","fee":80.0}
                    """))
        .andExpect(status().isBadRequest())
        .andExpect(header().string(CorrelationIdFilter.HEADER, "validation-test"))
        .andExpect(jsonPath("$.code").value("VALIDATION_FAILED"))
        .andExpect(jsonPath("$.correlationId").value("validation-test"))
        .andExpect(jsonPath("$.fields.courseCode").exists());
  }

  @Test
  void reportsDuplicateCourseCodeAsConflict() throws Exception {
    given(service.create(any()))
        .willThrow(
            new ConflictException(
                "MUSIC_COURSE_CODE_EXISTS",
                "Kursangebot mit diesem Kurscode existiert bereits"));

    mvc.perform(
            post("/api/courses")
                .header(CorrelationIdFilter.HEADER, "conflict-test")
                .contentType("application/json")
                .content("""
                    {"courseCode":"MU-01","title":"Zweiter Kurs","fee":80.0}
                    """))
        .andExpect(status().isConflict())
        .andExpect(jsonPath("$.code").value("MUSIC_COURSE_CODE_EXISTS"))
        .andExpect(jsonPath("$.correlationId").value("conflict-test"));
  }
}
