package course.bikerental.api;

import course.bikerental.service.StationService;
import course.bikerental.web.ApiError;
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
@RequestMapping("/api/stations")
public class StationController {
  private final StationService service;

  public StationController(StationService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Stationen lesen", operationId = "listStations")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Stationen",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = StationResponse.class))))
  @GetMapping
  public List<StationResponse> findAll() {
    return service.findAll().stream().map(StationResponse::from).toList();
  }

  @Operation(summary = "Station lesen", operationId = "getStation")
  @ApiResponse(
      responseCode = "200",
      description = "Station gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StationResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Station nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public StationResponse findById(@PathVariable long id) {
    return StationResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Station anlegen",
      operationId = "createStation",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateStationRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Station angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = StationResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "stationCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<StationResponse> create(@Valid @RequestBody CreateStationRequest request) {
    var response = StationResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
