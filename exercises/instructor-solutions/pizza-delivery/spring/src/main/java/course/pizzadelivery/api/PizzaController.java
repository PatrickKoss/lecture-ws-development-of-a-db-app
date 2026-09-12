package course.pizzadelivery.api;

import course.pizzadelivery.service.PizzaService;
import course.pizzadelivery.web.ApiError;
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
@RequestMapping("/api/pizzas")
public class PizzaController {
  private final PizzaService service;

  public PizzaController(PizzaService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Pizzen lesen", operationId = "listPizzas")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Pizzen",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = PizzaResponse.class))))
  @GetMapping
  public List<PizzaResponse> findAll() {
    return service.findAll().stream().map(PizzaResponse::from).toList();
  }

  @Operation(summary = "Pizza lesen", operationId = "getPizza")
  @ApiResponse(
      responseCode = "200",
      description = "Pizza gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = PizzaResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Pizza nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public PizzaResponse findById(@PathVariable long id) {
    return PizzaResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Pizza anlegen",
      operationId = "createPizza",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreatePizzaRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Pizza angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = PizzaResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "pizzaNumber darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<PizzaResponse> create(@Valid @RequestBody CreatePizzaRequest request) {
    var response = PizzaResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
