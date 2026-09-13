package com.example.restsimple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@Disabled("TODO C1: nach Ergänzung des Vertrags aktivieren")
@SpringBootTest
@AutoConfigureMockMvc
class OpenApiContractExerciseTest {
    @Autowired MockMvc mvc;
    @Test void documentsStudentContract() throws Exception {
        mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/students'].get").exists())
                .andExpect(jsonPath("$.paths['/api/students/{id}'].get").exists())
                .andExpect(jsonPath("$.paths['/api/students'].post").exists())
                .andExpect(jsonPath("$.paths['/api/students/{id}'].get.responses['400']").exists())
                .andExpect(jsonPath("$.paths['/api/students/{id}'].get.responses['404']").exists())
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['201'].headers.Location").exists())
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['400']").exists())
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['409']").exists())
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.id.readOnly").value(true))
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.firstName").exists())
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.lastName").exists())
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.email").exists())
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.studentNumber").exists())
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.enrollmentDate").exists())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.firstName").exists())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.lastName").exists())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.email").exists())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.studentNumber").exists())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.id").doesNotExist())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.enrollmentDate").doesNotExist());
    }
}
