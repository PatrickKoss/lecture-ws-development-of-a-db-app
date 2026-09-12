package course.museum.api;

import course.museum.service.ExhibitService;
import course.museum.web.ApiError;
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
@RequestMapping("/api/exhibits")
public class ExhibitController {
  private final ExhibitService service;

  public ExhibitController(ExhibitService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Exponate lesen", operationId = "listExhibits")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Exponate",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ExhibitResponse.class))))
  @GetMapping
  public List<ExhibitResponse> findAll() {
    return service.findAll().stream().map(ExhibitResponse::from).toList();
  }

  @Operation(summary = "Exponat lesen", operationId = "getExhibit")
  @ApiResponse(
      responseCode = "200",
      description = "Exponat gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ExhibitResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Exponat nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public ExhibitResponse findById(@PathVariable long id) {
    return ExhibitResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Exponat anlegen",
      operationId = "createExhibit",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateExhibitRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Exponat angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ExhibitResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "inventoryCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<ExhibitResponse> create(@Valid @RequestBody CreateExhibitRequest request) {
    var response = ExhibitResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
