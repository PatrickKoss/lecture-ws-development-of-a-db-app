package course.hotel.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@org.junit.jupiter.api.Disabled("TODO C1: nach Ergänzung des HTTP-Vertrags aktivieren")
class OpenApiContractExerciseTest {
  @Autowired MockMvc mvc;

  @Test
  void generatesContractWithoutCallingUnfinishedRepositoryMethods() throws Exception {
    mvc.perform(get("/v3/api-docs"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.paths['/api/room-types'].get.responses['200']").exists())
        .andExpect(jsonPath("$.paths['/api/room-types/{id}'].get.responses['404']").exists())
        .andExpect(jsonPath("$.paths['/api/room-types'].post.responses['201'].headers.Location").exists())
        .andExpect(jsonPath("$.paths['/api/room-types'].post.responses['400']").exists())
        .andExpect(jsonPath("$.paths['/api/room-types'].post.responses['409']").exists())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.id").exists())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.typeCode").exists())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.capacity").exists())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.standardPriceCents").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRoomTypeRequest.properties.typeCode").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRoomTypeRequest.properties.name").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRoomTypeRequest.properties.capacity").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRoomTypeRequest.properties.standardPriceCents").exists())
        .andExpect(jsonPath("$.components.schemas.CreateRoomTypeRequest.properties.id").doesNotExist())
        .andExpect(jsonPath("$.components.schemas.RoomTypeResponse.properties.id.readOnly").value(true));
  }
}
