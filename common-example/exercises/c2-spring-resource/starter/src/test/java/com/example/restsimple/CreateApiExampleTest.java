package com.example.restsimple;

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

@SpringBootTest
@AutoConfigureMockMvc
class CreateApiExampleTest {
    @Autowired MockMvc mvc;

    @Disabled("TODO C3: nach POST aktivieren")
    @Test void createsResource() throws Exception {
        var result = mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ada","lastName":"Lovelace",
                                 "email":"ada@campus.example","studentNumber":"M2026999"}
                                """))
                .andExpect(status().isCreated()).andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.studentNumber").value("M2026999")).andReturn();
        mvc.perform(get(result.getResponse().getHeader("Location"))).andExpect(status().isOk());
    }

    @Disabled("TODO C3: Konflikttest selbst schreiben und aktivieren")
    @Test void rejectsDuplicateBusinessKey() {
        throw new AssertionError("TODO C3: doppelte Matrikelnummer muss 409 liefern");
    }

    @Disabled("TODO C3: nach Bean Validation aktivieren")
    @Test void reportsValidationWithCorrelationId() throws Exception {
        mvc.perform(post("/api/students").contentType(MediaType.APPLICATION_JSON)
                        .header("X-Correlation-ID", "exercise-validation").content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.correlationId").value("exercise-validation"))
                .andExpect(jsonPath("$.fields.studentNumber").exists());
    }
}
