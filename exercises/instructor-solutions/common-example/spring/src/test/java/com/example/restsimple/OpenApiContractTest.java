package com.example.restsimple;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiContractTest {
    @Autowired MockMvc mvc;

    @Test void documentsStatusCodesErrorSchemasAndDtoFields() throws Exception {
        var result = mvc.perform(get("/v3/api-docs")).andExpect(status().isOk())
                .andExpect(jsonPath("$.paths['/api/students/{id}'].get.responses['400'].content['application/json'].schema['$ref']").value("#/components/schemas/ErrorResponse"))
                .andExpect(jsonPath("$.paths['/api/students/{id}'].get.responses['404'].content['application/json'].schema['$ref']").value("#/components/schemas/ErrorResponse"))
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['201'].headers.Location").exists())
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['400'].content['application/json'].schema['$ref']").value("#/components/schemas/ErrorResponse"))
                .andExpect(jsonPath("$.paths['/api/students'].post.responses['409'].content['application/json'].schema['$ref']").value("#/components/schemas/ErrorResponse"))
                .andExpect(jsonPath("$.components.schemas.StudentResponse.required.length()").value(6))
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.id.readOnly").value(true))
                .andExpect(jsonPath("$.components.schemas.StudentResponse.properties.enrollmentDate.readOnly").value(true))
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.required.length()").value(4))
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.id").doesNotExist())
                .andExpect(jsonPath("$.components.schemas.CreateStudentRequest.properties.enrollmentDate").doesNotExist())
                .andReturn();
        var output = Path.of("build/openapi/openapi.json");
        Files.createDirectories(output.getParent());
        Files.writeString(output, result.getResponse().getContentAsString());
    }
}
