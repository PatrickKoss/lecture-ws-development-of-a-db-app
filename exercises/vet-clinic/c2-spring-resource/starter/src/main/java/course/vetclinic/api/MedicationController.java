package course.vetclinic.api;

import course.vetclinic.service.MedicationService;
import course.vetclinic.web.ApiError;
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
@RequestMapping(value = "/api/medications", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Medication", description = "HTTP-Ressource für Medikamente")
public class MedicationController {
  private final MedicationService service;

  public MedicationController(MedicationService service) {
    this.service = service;
  }

  @Operation(summary = "Medikamente auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(
              array = @ArraySchema(schema = @Schema(implementation = MedicationResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<MedicationResponse> findAll() {
    return service.findAll().stream().map(MedicationResponse::from).toList();
  }

  @Operation(summary = "Medication nach ID lesen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource gefunden",
      content = @Content(schema = @Schema(implementation = MedicationResponse.class)))
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
  public MedicationResponse findById(@PathVariable @Positive long id) {
    return MedicationResponse.from(service.findById(id));
  }

  @Operation(summary = "Medication anlegen")
  @ApiResponse(
      responseCode = "201",
      description = "Ressource angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der neuen Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content = @Content(schema = @Schema(implementation = MedicationResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "pzn ist vergeben oder eine Datenbankregel wird verletzt",
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
  public ResponseEntity<MedicationResponse> create(
      @Valid @RequestBody CreateMedicationRequest request) {
    var response = MedicationResponse.from(service.create(request.toCommand()));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Medication vollständig ersetzen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource ersetzt",
      content = @Content(schema = @Schema(implementation = MedicationResponse.class)))
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
  public MedicationResponse replace(
      @PathVariable @Positive long id, @Valid @RequestBody UpdateMedicationRequest request) {
    return MedicationResponse.from(service.replace(id, request.toCommand()));
  }

  @Operation(
      summary = "Medication löschen",
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
