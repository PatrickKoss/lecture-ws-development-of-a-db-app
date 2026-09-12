package course.cinema.api;

import course.cinema.service.MovieService;
import course.cinema.web.ApiError;
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
@RequestMapping("/api/movies")
public class MovieController {
  private final MovieService service;

  public MovieController(MovieService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Filme lesen", operationId = "listMovies")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Filme",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = MovieResponse.class))))
  @GetMapping
  public List<MovieResponse> findAll() {
    return service.findAll().stream().map(MovieResponse::from).toList();
  }

  @Operation(summary = "Film lesen", operationId = "getMovie")
  @ApiResponse(
      responseCode = "200",
      description = "Film gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MovieResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Film nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public MovieResponse findById(@PathVariable long id) {
    return MovieResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Film anlegen",
      operationId = "createMovie",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateMovieRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Film angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = MovieResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "movieCode darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<MovieResponse> create(@Valid @RequestBody CreateMovieRequest request) {
    var response = MovieResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
