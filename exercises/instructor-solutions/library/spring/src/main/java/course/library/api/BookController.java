package course.library.api;

import course.library.service.BookService;
import course.library.web.ApiError;
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
@RequestMapping("/api/books")
public class BookController {
  private final BookService service;

  public BookController(BookService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Bücher lesen", operationId = "listBooks")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Bücher",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = BookResponse.class))))
  @GetMapping
  public List<BookResponse> findAll() {
    return service.findAll().stream().map(BookResponse::from).toList();
  }

  @Operation(summary = "Buch lesen", operationId = "getBook")
  @ApiResponse(
      responseCode = "200",
      description = "Buch gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = BookResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Buch nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public BookResponse findById(@PathVariable long id) {
    return BookResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Buch anlegen",
      operationId = "createBook",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateBookRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Buch angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = BookResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "isbn darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<BookResponse> create(@Valid @RequestBody CreateBookRequest request) {
    var response = BookResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
