package course.museum.api;

import course.museum.service.ExhibitService;
import course.museum.web.ApiError;
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
@RequestMapping(value = "/api/exhibits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Exhibit", description = "HTTP-Ressource für Exponate")
public class ExhibitController {
  private final ExhibitService service;

  public ExhibitController(ExhibitService service) {
    this.service = service;
  }

  @Operation(summary = "Exponate auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(array = @ArraySchema(schema = @Schema(implementation = ExhibitResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<ExhibitResponse> findAll() {
    return service.findAll().stream().map(ExhibitResponse::from).toList();
  }

  @Operation(summary = "Exhibit nach ID lesen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource gefunden",
      content = @Content(schema = @Schema(implementation = ExhibitResponse.class)))
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
  public ExhibitResponse findById(@PathVariable @Positive long id) {
    return ExhibitResponse.from(service.findById(id));
  }

  @Operation(summary = "Exhibit anlegen")
  @ApiResponse(
      responseCode = "201",
      description = "Ressource angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der neuen Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content = @Content(schema = @Schema(implementation = ExhibitResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "inventory_code ist vergeben oder eine Datenbankregel wird verletzt",
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
  public ResponseEntity<ExhibitResponse> create(@Valid @RequestBody CreateExhibitRequest request) {
    var response = ExhibitResponse.from(service.create(request.toCommand()));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Exhibit vollständig ersetzen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource ersetzt",
      content = @Content(schema = @Schema(implementation = ExhibitResponse.class)))
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
  public ExhibitResponse replace(
      @PathVariable @Positive long id, @Valid @RequestBody UpdateExhibitRequest request) {
    return ExhibitResponse.from(service.replace(id, request.toCommand()));
  }

  @Operation(
      summary = "Exhibit löschen",
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
