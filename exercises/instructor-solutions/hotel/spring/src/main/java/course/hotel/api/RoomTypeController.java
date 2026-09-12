package course.hotel.api;

import course.hotel.service.RoomTypeService;
import course.hotel.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/room-types")
public class RoomTypeController {
  private final RoomTypeService service;

  public RoomTypeController(RoomTypeService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Zimmertypen lesen", operationId = "listRoomTypes")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Zimmertypen",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = RoomTypeResponse.class))))
  @GetMapping
  public List<RoomTypeResponse> findAll() {
    return service.findAll().stream().map(RoomTypeResponse::from).toList();
  }

  @Operation(summary = "Zimmertyp lesen", operationId = "getRoomType")
  @ApiResponse(
      responseCode = "200",
      description = "Zimmertyp gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RoomTypeResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Zimmertyp nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public RoomTypeResponse findById(@PathVariable long id) {
    return RoomTypeResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Zimmertyp anlegen",
      operationId = "createRoomType",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateRoomTypeRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Zimmertyp angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RoomTypeResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "typeCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<RoomTypeResponse> create(@Valid @RequestBody CreateRoomTypeRequest request) {
    var response = RoomTypeResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
