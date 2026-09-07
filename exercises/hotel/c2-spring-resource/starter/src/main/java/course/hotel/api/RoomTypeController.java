package course.hotel.api;

import course.hotel.service.RoomTypeService;
import course.hotel.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {
  private final RoomTypeService service;

  public RoomTypeController(RoomTypeService service) {
    this.service = service;
  }

  @GetMapping
  public List<RoomTypeResponse> findAll() {
    return service.findAll().stream().map(RoomTypeResponse::from).toList();
  }

  @GetMapping("/{id}")
  public RoomTypeResponse findById(@PathVariable long id) {
    return RoomTypeResponse.from(service.findById(id));
  }

  // Beispiel für C1. Dokumentiert die GET-Methoden nach diesem Muster.
  @Operation(summary = "RoomType anlegen",
      requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
          required = true,
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = CreateRoomTypeRequest.class))))
  @ApiResponse(responseCode = "201", description = "Ressource angelegt",
      headers = @Header(name = "Location", description = "URL der neuen Ressource",
          schema = @Schema(type = "string", format = "uri")),
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = RoomTypeResponse.class)))
  @ApiResponse(responseCode = "400", description = "Eingabe verletzt die Validierung",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(responseCode = "409", description = "Eindeutiger Wert bereits vergeben",
      content = @Content(mediaType = "application/json",
          schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<RoomTypeResponse> create(@Valid @RequestBody CreateRoomTypeRequest request) {
    var response = RoomTypeResponse.from(service.create(request));
    var location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(response.id()).toUri();
    return ResponseEntity.created(location).body(response);
  }
}
