package course.eventtickets.api;

import course.eventtickets.service.VenueService;
import course.eventtickets.web.ApiError;
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
@RequestMapping("/api/venues")
public class VenueController {
  private final VenueService service;

  public VenueController(VenueService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Spielorte lesen", operationId = "listVenues")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Spielorte",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = VenueResponse.class))))
  @GetMapping
  public List<VenueResponse> findAll() {
    return service.findAll().stream().map(VenueResponse::from).toList();
  }

  @Operation(summary = "Spielort lesen", operationId = "getVenue")
  @ApiResponse(
      responseCode = "200",
      description = "Spielort gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = VenueResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Spielort nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public VenueResponse findById(@PathVariable long id) {
    return VenueResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Spielort anlegen",
      operationId = "createVenue",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateVenueRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Spielort angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = VenueResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "venueCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<VenueResponse> create(@Valid @RequestBody CreateVenueRequest request) {
    var response = VenueResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
