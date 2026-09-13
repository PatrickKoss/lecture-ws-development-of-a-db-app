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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    throw new UnsupportedOperationException("TODO C2: Liste über Service und Response-Mapping liefern");
  }

  @Operation(summary = "Movie nach ID lesen")
  // TODO C1: Dokumentiert 200, 400 und 404 mit den passenden Schemas.
  @GetMapping("/{id}")
  public MovieResponse findById(@PathVariable @Positive long id) {
    throw new UnsupportedOperationException("TODO C2: Einzelressource über Service und Response-Mapping liefern");
  }

  @Operation(summary = "Movie anlegen")
  // TODO C1: Dokumentiert 201 mit Location sowie 400, 409 und 415.
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<MovieResponse> create(@Valid @RequestBody CreateMovieRequest request) {
    throw new UnsupportedOperationException("TODO C3: Anlegen und Location-Header implementieren");
  }

}
