package course.foodmarketplace.api;

import course.foodmarketplace.service.RestaurantService;
import course.foodmarketplace.web.ApiError;
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
@RequestMapping("/api/restaurants")
public class RestaurantController {
  private final RestaurantService service;

  public RestaurantController(RestaurantService service) {
    this.service = service;
  }

  @Operation(summary = "Alle Restaurants lesen", operationId = "listRestaurants")
  @ApiResponse(
      responseCode = "200",
      description = "Liste aller Restaurants",
      content =
          @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = RestaurantResponse.class))))
  @GetMapping
  public List<RestaurantResponse> findAll() {
    return service.findAll().stream().map(RestaurantResponse::from).toList();
  }

  @Operation(summary = "Restaurant lesen", operationId = "getRestaurant")
  @ApiResponse(
      responseCode = "200",
      description = "Restaurant gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RestaurantResponse.class)))
  @ApiResponse(
      responseCode = "404",
      description = "Restaurant nicht gefunden",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @GetMapping("/{id}")
  public RestaurantResponse findById(@PathVariable long id) {
    return RestaurantResponse.from(service.findById(id));
  }

  @Operation(
      summary = "Restaurant anlegen",
      operationId = "createRestaurant",
      requestBody =
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              required = true,
              content =
                  @Content(
                      mediaType = "application/json",
                      schema = @Schema(implementation = CreateRestaurantRequest.class))))
  @ApiResponse(
      responseCode = "201",
      description = "Restaurant angelegt",
      headers =
          @Header(
              name = "Location",
              description = "URL der angelegten Ressource",
              schema = @Schema(type = "string", format = "uri")),
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = RestaurantResponse.class)))
  @ApiResponse(
      responseCode = "400",
      description = "Eingabe verletzt die Validierung",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @ApiResponse(
      responseCode = "409",
      description = "partnerNumber darf nicht doppelt vorkommen",
      content =
          @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiError.class)))
  @PostMapping
  public ResponseEntity<RestaurantResponse> create(@Valid @RequestBody CreateRestaurantRequest request) {
    var response = RestaurantResponse.from(service.create(request));
    var location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.id())
            .toUri();
    return ResponseEntity.created(location).body(response);
  }
}
