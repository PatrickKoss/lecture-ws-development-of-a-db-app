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

@SpringBootTest
@AutoConfigureMockMvc
class ReadApiExerciseTest {
    @Autowired MockMvc mvc;

    @Disabled("TODO C2: nach Implementierung der GET-Liste aktivieren")
    @Test void readsSeedData() throws Exception {
        mvc.perform(get("/api/students")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentNumber").value("M2023001"));
    }

    @Disabled("TODO C3: 404-Test schreiben und aktivieren")
    @Test void reportsUnknownId() {
        throw new AssertionError("TODO C3: GET einer unbekannten ID muss 404 liefern");
    }
}
