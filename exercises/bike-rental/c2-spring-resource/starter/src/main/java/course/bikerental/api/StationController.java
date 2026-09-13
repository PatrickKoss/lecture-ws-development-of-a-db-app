package course.bikerental.api;

import course.bikerental.service.StationService;
import course.bikerental.web.ApiError;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping(value = "/api/stations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Station", description = "HTTP-Ressource für Stationen")
public class StationController {
  private final StationService service;

  public StationController(StationService service) {
    this.service = service;
  }

  @Operation(summary = "Stationen auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(array = @ArraySchema(schema = @Schema(implementation = StationResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<StationResponse> findAll() {
    throw new UnsupportedOperationException("TODO C2: Liste über Service und Response-Mapping liefern");
  }

  @Operation(summary = "Station nach ID lesen")
  // TODO C1: Dokumentiert 200, 400 und 404 mit den passenden Schemas.
  @GetMapping("/{id}")
  public StationResponse findById(@PathVariable @Positive long id) {
    throw new UnsupportedOperationException("TODO C2: Einzelressource über Service und Response-Mapping liefern");
  }

  @Operation(summary = "Station anlegen")
  // TODO C1: Dokumentiert 201 mit Location sowie 400, 409 und 415.
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<StationResponse> create(@Valid @RequestBody CreateStationRequest request) {
    throw new UnsupportedOperationException("TODO C3: Anlegen und Location-Header implementieren");
  }

}
