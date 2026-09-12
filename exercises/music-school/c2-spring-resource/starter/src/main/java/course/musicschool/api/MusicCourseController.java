package course.musicschool.api;

import course.musicschool.service.MusicCourseService;
import course.musicschool.web.ApiError;
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
@RequestMapping(value = "/api/courses", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MusicCourse", description = "HTTP-Ressource für Kursangebote")
public class MusicCourseController {
  private final MusicCourseService service;

  public MusicCourseController(MusicCourseService service) {
    this.service = service;
  }

  @Operation(summary = "Kursangebote auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(
              array = @ArraySchema(schema = @Schema(implementation = MusicCourseResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<MusicCourseResponse> findAll() {
    return service.findAll().stream().map(MusicCourseResponse::from).toList();
  }

  @Operation(summary = "MusicCourse nach ID lesen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource gefunden",
      content = @Content(schema = @Schema(implementation = MusicCourseResponse.class)))
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
  public MusicCourseResponse findById(@PathVariable @Positive long id) {
    return MusicCourseResponse.from(service.findById(id));
  }

  @Operation(summary = "MusicCourse anlegen")
  @ApiResponse(
      responseCode = "201",
      description = "Ressource angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der neuen Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content = @Content(schema = @Schema(implementation = MusicCourseResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "course_code ist vergeben oder eine Datenbankregel wird verletzt",
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
  public ResponseEntity<MusicCourseResponse> create(
      @Valid @RequestBody CreateMusicCourseRequest request) {
    var response = MusicCourseResponse.from(service.create(request.toCommand()));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "MusicCourse vollständig ersetzen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource ersetzt",
      content = @Content(schema = @Schema(implementation = MusicCourseResponse.class)))
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
  public MusicCourseResponse replace(
      @PathVariable @Positive long id, @Valid @RequestBody UpdateMusicCourseRequest request) {
    return MusicCourseResponse.from(service.replace(id, request.toCommand()));
  }

  @Operation(
      summary = "MusicCourse löschen",
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
