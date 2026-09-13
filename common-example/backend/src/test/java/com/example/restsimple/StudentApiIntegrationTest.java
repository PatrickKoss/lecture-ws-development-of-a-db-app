package com.example.restsimple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class StudentApiIntegrationTest {
    @Autowired MockMvc mvc;

    @Test
    void readsCanonicalSeedDataAndReportsMissingIds() throws Exception {
        mvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentNumber").value("M2023001"));
        mvc.perform(get("/api/students/999999"))
                .andExpect(status().isNotFound());
        mvc.perform(get("/api/students/-1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
        mvc.perform(get("/api/students/not-a-number"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
    }

    @Test
    void createsStudentWithServerAssignedEnrollmentDate() throws Exception {
        var created = mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ada","lastName":"Lovelace",
                                 "email":"ada@campus.example","studentNumber":"M2026999"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.enrollmentDate").exists())
                .andReturn();
        mvc.perform(get(created.getResponse().getHeader("Location")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentNumber").value("M2026999"));
    }

    @Test
    void rejectsDuplicateStudentNumbersAndInvalidRequests() throws Exception {
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ada","lastName":"Lovelace",
                                 "email":"new@campus.example","studentNumber":"M2023001"}
                                """))
                .andExpect(status().isConflict());
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-Correlation-ID", "solution-test")
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.correlationId").value("solution-test"));
        mvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{broken"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_JSON"));
    }
}
