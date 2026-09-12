package course.carworkshop.api;

import course.carworkshop.service.PartService;
import course.carworkshop.web.ApiError;
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
@RequestMapping("/api/parts")
public class PartController {
  private final PartService service;

  public PartController(PartService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Ersatzteile lesen", operationId = "listParts")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Ersatzteile",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = PartResponse.class))))
  @GetMapping
  public List<PartResponse> findAll() {
    return service.findAll().stream().map(PartResponse::from).toList();
  }

  @Operation(summary = "Ersatzteil lesen", operationId = "getPart")
  @ApiResponse(
      responseCode = "200",
      description = "Ersatzteil gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = PartResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Ersatzteil nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public PartResponse findById(@PathVariable long id) {
    return PartResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Ersatzteil anlegen",
      operationId = "createPart",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreatePartRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Ersatzteil angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = PartResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "partNumber darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<PartResponse> create(@Valid @RequestBody CreatePartRequest request) {
    var response = PartResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
