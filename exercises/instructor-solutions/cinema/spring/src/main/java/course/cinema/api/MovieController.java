package course.cinema.api;

import course.cinema.service.MovieService;
import course.cinema.web.ApiError;
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
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Movie", description = "HTTP-Ressource für Filme")
public class MovieController {
  private final MovieService service;

  public MovieController(MovieService service) {
    this.service = service;
  }

  @Operation(summary = "Filme auflisten")
  @ApiResponse(
      responseCode = "200",
      description = "Nach ID sortierte Liste",
      content =
          @Content(array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
  @ApiResponse(
      responseCode = "406",
      description = "Angeforderte Repräsentation ist nicht verfügbar",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "500",
      description = "Unerwarteter Serverfehler",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @GetMapping
  public List<MovieResponse> findAll() {
    return service.findAll().stream().map(MovieResponse::from).toList();
  }

  @Operation(summary = "Movie nach ID lesen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource gefunden",
      content = @Content(schema = @Schema(implementation = MovieResponse.class)))
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
  public MovieResponse findById(@PathVariable @Positive long id) {
    return MovieResponse.from(service.findById(id));
  }

  @Operation(summary = "Movie anlegen")
  @ApiResponse(
      responseCode = "201",
      description = "Ressource angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der neuen Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content = @Content(schema = @Schema(implementation = MovieResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe oder JSON ist ungültig",
      content = @Content(schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "movie_code ist vergeben oder eine Datenbankregel wird verletzt",
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
  public ResponseEntity<MovieResponse> create(@Valid @RequestBody CreateMovieRequest request) {
    var response = MovieResponse.from(service.create(request.toCommand()));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }

  @Operation(summary = "Movie vollständig ersetzen")
  @ApiResponse(
      responseCode = "200",
      description = "Ressource ersetzt",
      content = @Content(schema = @Schema(implementation = MovieResponse.class)))
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
  public MovieResponse replace(
      @PathVariable @Positive long id, @Valid @RequestBody UpdateMovieRequest request) {
    return MovieResponse.from(service.replace(id, request.toCommand()));
  }

  @Operation(
      summary = "Movie löschen",
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
