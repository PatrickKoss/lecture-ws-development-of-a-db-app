package course.eventtickets.api;

import course.eventtickets.service.VenueService;
import course.eventtickets.web.ApiError;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@Validated
@RequestMapping(value = "/api/venues", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Venue", description = "HTTP-Ressource für Spielorte")
public class VenueController {
  private final VenueService service;

  public VenueController(VenueService service) {
    this.service = service;
  }

  @Operation(summary = "Spielorte auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(array = @ArraySchema(schema = @Schema(implementation = VenueResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<VenueResponse> findAll() {
    return service.findAll().stream().map(VenueResponse::from).toList();
  }

  @Operation(summary = "Venue nach ID lesen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource gefunden",
      content = @Content(schema = @Schema(implementation = VenueResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "ID ist unbekannt",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "400",
      description = "ID ist keine positive Zahl",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public VenueResponse findById(@PathVariable @Positive long id) {
    return VenueResponse.from(service.findById(id));
  }

  @Operation(summary = "Venue anlegen")
  @ApiResponse(
      responseCode = "201",
      description = "Ressource angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der neuen Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content = @Content(schema = @Schema(implementation = VenueResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "venue_code ist vergeben oder eine Datenbankregel wird verletzt",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "415",
      description = "Content-Type ist nicht application/json",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<VenueResponse> create(@Valid @RequestBody CreateVenueRequest request) {
    var response = VenueResponse.from(service.create(request.toCommand()));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Venue vollständig ersetzen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource ersetzt",
      content = @Content(schema = @Schema(implementation = VenueResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "404",
      description = "ID ist unbekannt",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "Fachschlüssel oder Datenbankregel kollidiert",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "415",
      description = "Content-Type ist nicht application/json",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
  public VenueResponse replace(
      @PathVariable @Positive long id, @Valid @RequestBody UpdateVenueRequest request) {
    return VenueResponse.from(service.replace(id, request.toCommand()));
  }

  @Operation(
      summary = "Venue löschen",
      description = "Idempotent: Eine unbekannte ID liefert ebenfalls 204.")
  @ApiResponse(responseCode = "204", description = "Ressource ist gelöscht")
  @ApiResponse(
      responseCode = "409",
      description = "Referenzierende Daten verhindern das Löschen",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "400",
      description = "ID ist keine positive Zahl",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable @Positive long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }
}
